package com.flexpag.paymentscheduler.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

	@Autowired
	private PaymentRepository repository;

	@GetMapping
	public ResponseEntity<List<PaymentModel>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentModel> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<PaymentModel> save(@RequestBody PaymentModel payment) {
		if (payment.getPaymentDate().equals(Instant.now())) {
			payment.setStatus(PaymentStatus.PAID);
			System.out.println("O pagamento foi realizado hoje.");
		}

		else if (payment.getPaymentDate().isBefore(Instant.now())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		else {
			payment.setStatus(PaymentStatus.PENDING);
			System.out.println("O pagamento foi agendado.");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(payment));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PaymentModel> update(@PathVariable Long id, @RequestBody PaymentModel payment) {
	    Optional<PaymentModel> paymentData = repository.findById(id);

	    if (paymentData.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	    PaymentModel paymentUpdate = paymentData.get();
	    
	    if (paymentUpdate.getStatus() == PaymentStatus.PAID && payment.getPaymentDate().isBefore(Instant.now())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    } 
	    
	    else if (paymentUpdate.getStatus() == PaymentStatus.PENDING) {
	        if (payment.getPaymentDate().isAfter(Instant.now())) {
	            paymentUpdate.setPaymentDate(payment.getPaymentDate());
	            paymentUpdate.setStatus(PaymentStatus.PENDING);
	            return ResponseEntity.ok(repository.save(paymentUpdate));
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	        }
	    } 
	    
	    else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<PaymentModel> paymentData = repository.findById(id);

		if (paymentData.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		PaymentModel payment = paymentData.get();
		
		if (payment.getStatus() == PaymentStatus.PENDING) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
}

