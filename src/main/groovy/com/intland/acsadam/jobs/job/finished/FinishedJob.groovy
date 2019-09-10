package com.intland.acsadam.jobs.job.finished

import com.intland.acsadam.jobs.BaseEntity
import com.intland.acsadam.jobs.job.Job

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "finishedjob")
class FinishedJob extends BaseEntity {
    @ManyToOne
    Job job

    LocalDateTime finishTime

    @Override
    String toString() {
        return "${job} finished on ${finishTime.format(DateTimeFormatter.ISO_DATE_TIME)}"
    }
}
