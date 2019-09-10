package com.intland.acsadam.jobs.job.finished

import org.springframework.data.jpa.repository.JpaRepository

interface FinishedJobRepo extends JpaRepository<FinishedJob, Long>{

}