package com.intland.acsadam.jobs.job.running

import com.intland.acsadam.jobs.job.Job
import org.springframework.stereotype.Component

@Component
class JobManager {
    Map<Job, RunningJob> runningJobs = [:]

    boolean existsByJobType(String jobType) {
        return runningJobs.values().stream()
                .map({ rj -> rj.job.type })
                .anyMatch({ type -> type == jobType })
    }

    RunningJob execute(Job job) {
        def runningJob = new RunningJob(job: job)
        runningJobs.put(runningJob.job, runningJob)
        return runningJob
    }

    RunningJob update(RunningJob runningJob) {
        if (runningJobs.containsKey(runningJob.job)) {
            runningJobs.put(runningJob.job, runningJob)
        }
        return runningJob
    }

    List<RunningJob> getRunningJobs() {
        return runningJobs.values().toList()
    }

    void finish(RunningJob runningJob) {
        runningJobs.remove(runningJob.job)
    }
}
