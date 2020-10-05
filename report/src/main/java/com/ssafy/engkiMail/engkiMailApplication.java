package com.ssafy.engkiMail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class engkiMailApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(engkiMailApplication.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler() {

		return new ConcurrentTaskScheduler();
	}
}
