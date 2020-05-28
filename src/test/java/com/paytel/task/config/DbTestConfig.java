package com.paytel.task.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.paytel.task.repository"})
@EnableJpaRepositories(basePackages = {"com.paytel.task.repository"})
@EntityScan(basePackages = {"com.paytel.task.model"})
public class DbTestConfig {

}
