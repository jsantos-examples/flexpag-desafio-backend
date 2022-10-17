package com.flexpag.paymentscheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.model.dto.StatusPaymentsDto;
import com.flexpag.paymentscheduler.servirce.PaymentService;
import com.flexpag.paymentscheduler.status.Status;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@GetMapping
	public ResponseEntity<List<Payment>> listAll() {
		List<Payment> payments = paymentService.listAll();
		return ResponseEntity.ok().body(payments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id) {
		Payment payment = paymentService.listById(id);
		return ResponseEntity.ok().body(payment);

	}

	@PostMapping
	public ResponseEntity<Long> savePayment(@RequestBody Payment payment) {
		payment = paymentService.create(payment);
		return ResponseEntity.ok().body(payment.getId());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		paymentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<Status> findStatusById(@PathVariable Long id) {
		StatusPaymentsDto payment = paymentService.listStatusId(id);
		return ResponseEntity.ok().body(payment.getStatus());

	}

	@PutMapping("/{id}")
	public ResponseEntity<Payment> updateByDate(@PathVariable Long id, @RequestBody Payment payment) {

		Payment pay = paymentService.update(id, payment);
		return ResponseEntity.ok().body(pay);
	}

}