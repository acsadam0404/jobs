package com.intland.acsadam.jobs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class JobsApplication  extends SpringBootServletInitializer {

	static void main(String[] args) {
		SpringApplication.run(JobsApplication, args)
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JobsApplication)
	}

}
