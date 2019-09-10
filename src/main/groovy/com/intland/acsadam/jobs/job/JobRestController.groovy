package com.intland.acsadam.jobs.job

import com.intland.acsadam.jobs.job.JobService.JobTypeInUseException
import com.intland.acsadam.jobs.job.running.RunningJob
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime
import java.util.stream.Collectors

@RestController
@RequestMapping("/rest/jobs")
class JobRestController {
    private final JobService jobService

    @Autowired
    JobRestController(JobService jobService) {
        this.jobService = jobService
    }

    @GetMapping("")
    public List<JobDto> listJobs() {
        return jobService.findAll()
                .stream()
                .map({ job -> new JobDto(name: job.name, type: job.type) })
                .collect(Collectors.toList())
    }

    @GetMapping("/running")
    List<RunningJobDto> listRunningJobs() {
        return jobService.findAllRunning()
                .stream()
                .map({ runningJob -> new RunningJobDto(name: runningJob.job.name, type: runningJob.job.type, progress: runningJob.progress) })
                .collect(Collectors.toList())
    }

    @GetMapping("/finished")
    List<FinishedJobDto> listFinishedJobs() {
        return jobService.findAllFinished()
                .stream()
                .map({ finishedJob -> new FinishedJobDto(name: finishedJob.job.name, type: finishedJob.job.type, finishTime: finishedJob.finishTime) })
                .collect(Collectors.toList())
    }

    @PostMapping("/run")
    ResponseEntity executeJob(@RequestBody Job job) {
        try {
            jobService.execute(job)
            return new ResponseEntity(HttpStatus.OK)
        } catch (JobTypeInUseException e) {
            return new ResponseEntity("A job with type ${e.job.type} is already running", HttpStatus.BAD_REQUEST)
        }
    }

    static class JobDto {
        String name
        String type
    }

    static class RunningJobDto {
        String name
        String type
        int progress
    }

    static class FinishedJobDto {
        String name
        String type
        LocalDateTime finishTime
    }
}
