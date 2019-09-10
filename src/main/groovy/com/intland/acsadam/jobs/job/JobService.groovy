package com.intland.acsadam.jobs.job

import com.intland.acsadam.jobs.job.finished.FinishedJob
import com.intland.acsadam.jobs.job.running.RunningJob

interface JobService {
    void execute(Job job) throws JobExecutionException

    Job save(Job job)

    List<Job> findAll()

    List<RunningJob> findAllRunning()

    List<FinishedJob> findAllFinished()

    static class JobExecutionException extends Exception {
        final Job job

        JobExecutionException(Job job, String message) {
            super(message)
            this.job = job
        }
    }

    static class JobTypeInUseException extends JobExecutionException{

        JobTypeInUseException(Job job, String message) {
            super(job, message)
        }
    }
}