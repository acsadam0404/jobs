package com.intland.acsadam.jobs.job.running


import com.intland.acsadam.jobs.job.Job
import groovy.transform.EqualsAndHashCode

import javax.persistence.ManyToOne

@EqualsAndHashCode(includes = [RunningJob.JOB])
class RunningJob {
    public static final String PROGRESS = "progress"
    public static final String JOB = "job"
    public static final String PARAMETERS = "parameters"

    int progress

    @ManyToOne
    Job job

    String parameters

    @Override
    String toString() {
        return job.toString() + " @ " + progress + "%"
    }
}
