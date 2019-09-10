package com.intland.acsadam.jobs.job

interface JobSchedulerService {
    void schedule(Job job, String cron)
}