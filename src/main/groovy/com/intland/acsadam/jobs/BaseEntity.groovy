package com.intland.acsadam.jobs

import groovy.transform.EqualsAndHashCode

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@EqualsAndHashCode(includes = [BaseEntity.ID])
@MappedSuperclass
class BaseEntity {
    public static final String ID = "id"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    long id
}
