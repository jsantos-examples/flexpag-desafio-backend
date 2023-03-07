package com.flexpag.paymentscheduler.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexpag.paymentscheduler.model.PaymentModel;
import com.flexpag.paymentscheduler.model.status.PaymentStatus;
import com.flexpag.paymentscheduler.service.PaymentService;

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public ResponseEntity<List<PaymentModel>> getAll() {
		List<PaymentModel> payments = paymentService.getAll();
		return ResponseEntity.ok(payments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentModel> getById(@PathVariable long id) {
		return paymentService.getById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<PaymentModel>> getByStatus(@PathVariable int status) {
		List<PaymentModel> statusPayment = paymentService.getByStatus(status);
		return ResponseEntity.ok(statusPayment);
	}

	@PostMapping
	public ResponseEntity<PaymentModel> post(@RequestBody PaymentModel payment) {
		if (payment.getPaymentDate().isAfter(Instant.now())) {
			payment.setStatus(PaymentStatus.PENDING);
			PaymentModel savedPayment = paymentService.postSchedule(payment);
			return ResponseEntity.ok(savedPayment);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaymentModel> update(@PathVariable Long id, @RequestBody PaymentModel payment) {
		try {
			PaymentModel updatedPayment = paymentService.updateSchedule(id, payment);
			return ResponseEntity.ok(updatedPayment);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
		try {
			paymentService.deleteSchedule(id);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}