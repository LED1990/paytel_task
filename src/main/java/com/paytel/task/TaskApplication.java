package com.paytel.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
		log.info("info log");
		log.error("error log");
		log.debug("debug log");
		log.trace("trace log");
		log.warn("warn log");

		SpringApplication.run(TaskApplication.class, args);
	}

}
