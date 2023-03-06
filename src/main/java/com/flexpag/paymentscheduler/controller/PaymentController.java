package com.flexpag.paymentscheduler.controller;

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
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import com.flexpag.paymentscheduler.service.PaymentService;

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PaymentRepository paymentRepository;
	
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
		PaymentStatus paymentStatus = PaymentStatus.valueOf(status);
		List<PaymentModel> payments = paymentRepository.findByStatus(paymentStatus);
		return ResponseEntity.ok(payments);
	}

	@PostMapping
	public ResponseEntity<PaymentModel> post(@RequestBody PaymentModel payment) {
		if (paymentService.postSchedule(payment) == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(payment);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaymentModel> update(@PathVariable Long id, @RequestBody PaymentModel payment) {
		PaymentModel updatedPayment = paymentService.updateSchedule(id, payment);
		return ResponseEntity.ok(updatedPayment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PaymentModel> delete(@PathVariable Long id) {
		try {
			PaymentModel deletePayment = paymentService.deleteSchedule(id);
			return ResponseEntity.ok(deletePayment);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}