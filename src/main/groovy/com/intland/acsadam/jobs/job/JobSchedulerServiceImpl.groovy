package com.intland.acsadam.jobs.job

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service

@Service
class JobSchedulerServiceImpl implements JobSchedulerService{
    private static final Logger logger = LoggerFactory.getLogger(JobSchedulerServiceImpl)
    private final TaskScheduler scheduler
    private final JobService jobService

    @Autowired
    JobSchedulerServiceImpl(TaskScheduler scheduler, JobService jobService) {
        this.jobService = jobService
        this.scheduler = scheduler
    }

    @Override
    void schedule(Job job, String cron) {
        CronTrigger cronTrigger = new CronTrigger(cron, TimeZone.getDefault())
        scheduler.schedule({
            jobService.execute(job)
        }, cronTrigger)
    }
}
