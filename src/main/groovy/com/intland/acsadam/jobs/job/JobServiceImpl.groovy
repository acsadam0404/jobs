package com.intland.acsadam.jobs.job

import com.intland.acsadam.jobs.job.finished.FinishedJob
import com.intland.acsadam.jobs.job.finished.FinishedJobRepo
import com.intland.acsadam.jobs.job.running.JobManager
import com.intland.acsadam.jobs.job.running.RunningJob
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.lang.reflect.Method
import java.time.LocalDateTime

@Service
class JobServiceImpl implements JobService{
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl)

    private final JobRepo repo
    private final JobManager jobManager
    private final FinishedJobRepo finishedJobRepo

    @Autowired
    JobServiceImpl(JobRepo repo, FinishedJobRepo finishedJobRepo, JobManager jobManager) {
        this.repo = repo
        this.finishedJobRepo = finishedJobRepo
        this.jobManager = jobManager
    }

    @Override
    @Async
    void execute(Job job) throws JobExecutionException {
        job = repo.findByName(job.name).orElseThrow({ new JobExecutionException(job, "No such job")})
        if (jobManager.existsByJobType(job.type)) {
            throw new JobTypeInUseException(job, "Already executing a job with the same type")
        }

        try {
            RunningJob runningJob = jobManager.execute(job)

            10.times {
                runningJob = mockRunning(runningJob)
            }

            jobManager.finish(runningJob)
            finishedJobRepo.save(new FinishedJob(job: runningJob.job, finishTime: LocalDateTime.now()))

        } catch (Exception e) {
            logger.error(e.message, e)
            throw new JobExecutionException(job, "${job} job cannot be executed.")
        }
    }

    private RunningJob mockRunning(RunningJob runningJob) {
        Thread.sleep(1000)
        runningJob.progress += 10
        return jobManager.update(runningJob)
    }

    /**
     * executes an arbitrary groovy script
     * @param jobScript
     * @return
     */
    private Object executeScript(String jobScript) {
        GroovyShell shell = new GroovyShell();
        Object script = shell.evaluate("""
                    def exec() {
                        ${jobScript}
                    };
            return this
        """);
        Method m = script.getClass().getMethod("exec");
        return m.invoke(script);
    }

    @Override
    Job save(Job job) {
        return repo.save(job)
    }

    @Override
    List<Job> findAll() {
        return repo.findAll()
    }

    @Override
    List<RunningJob> findAllRunning() {
        return jobManager.getRunningJobs()
    }

    @Override
    List<FinishedJob> findAllFinished() {
        return finishedJobRepo.findAll()
    }
}
