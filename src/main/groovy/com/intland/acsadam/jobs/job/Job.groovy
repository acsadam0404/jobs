package com.intland.acsadam.jobs.job

import com.intland.acsadam.jobs.BaseEntity
import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "job")
@EqualsAndHashCode(includes = [Job.NAME])
class Job extends BaseEntity{

    public static final String NAME = "name"
    public static final String SCRIPT = "script"
    public static final String TYPE = "type"


    @NotEmpty
    @Column(unique = true)
    String name

    @Column(columnDefinition = "TEXT")
    @NotEmpty
    String script

    String type

    @Override
    String toString() {
        return name
    }
}
