package com.intland.acsadam.jobs.job

import org.springframework.data.jpa.repository.JpaRepository

interface JobRepo extends JpaRepository<Job, Long>{
    Optional<Job> findByName(String name)
}