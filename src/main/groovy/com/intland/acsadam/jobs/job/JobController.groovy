package com.intland.acsadam.jobs.job

import com.intland.acsadam.jobs.job.running.RunningJob
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

import javax.validation.Valid
import java.util.stream.Collectors

@Controller
class JobController {
    private final JobService jobService

    @Autowired
    JobController(JobService jobService) {
        this.jobService = jobService
    }

    @GetMapping("/jobs/create")
    ModelAndView createJob() {
        return new ModelAndView("job_create", "job", new Job());
    }

    @PostMapping("/jobs/create")
    public ModelAndView createJob(@Valid @ModelAttribute("job") Job job, BindingResult result) {
        if (result.hasErrors()) {
            String errorString = result.allErrors.stream().map({ e -> "${e.field} ${e.defaultMessage}" }).collect(Collectors.joining("<br/>"))
            return new ModelAndView("job_create", [errors: errorString])
        }

        try {
            jobService.save(job)
        } catch (DataIntegrityViolationException e) {
            return new ModelAndView("job_create", [errors: "A job with the same name already exists"])
        }
        return listJobs()
    }

    @GetMapping("/")
    public ModelAndView listJobs() {
        return new ModelAndView("jobs")
    }

}
