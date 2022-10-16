package com.flexpag.paymentscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableScheduling
@EnableWebMvc
@EnableSwagger2
public class PaymentSchedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentSchedulerApplication.class, args);
	}

}
