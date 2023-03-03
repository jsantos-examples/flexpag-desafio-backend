package com.flexpag.paymentscheduler;

import com.flexpag.paymentscheduler.model.PaymentScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class PaymentSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSchedulerApplication.class, args);
	}


	@GetMapping("/")
		public ResponseEntity<PaymentScheduling> tutorialPage(){
		PaymentScheduling paymentScheduling = new PaymentScheduling(LocalDateTime.now(), 500.0);

		return ResponseEntity.ok(paymentScheduling);
	}

}
