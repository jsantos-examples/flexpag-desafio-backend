package com.flexpag.paymentscheduler.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.model.PaymentModel;
import com.flexpag.paymentscheduler.model.status.PaymentStatus;
import com.flexpag.paymentscheduler.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	public List<PaymentModel> getAll() {
		return paymentRepository.findAll();
	}

	public Optional<PaymentModel> getById(Long id) {
		return paymentRepository.findById(id);
	}

	public PaymentModel postSchedule(PaymentModel payment) {
		if (payment.getPaymentDate().isBefore(Instant.now())) {
			throw new IllegalArgumentException();
		} else {
			payment.setStatus(PaymentStatus.PENDING);
			return paymentRepository.save(payment);
		}
	}

	public PaymentModel updateSchedule(Long id, PaymentModel payment) {
		Optional<PaymentModel> paymentId = paymentRepository.findById(id);
		PaymentModel scheduleUpdate = paymentId.get();
		if (payment.getPaymentDate().isAfter(Instant.now())) {
			scheduleUpdate.setPaymentDate(payment.getPaymentDate());
			return paymentRepository.save(scheduleUpdate);
		}
		throw new IllegalArgumentException();
	}

	public PaymentModel deleteSchedule(Long id) {
		Optional<PaymentModel> paymentId = paymentRepository.findById(id);
		if (paymentId.get().getStatus() == PaymentStatus.PENDING) {
			paymentRepository.deleteById(id);
		}
		throw new IllegalArgumentException();
	}

	
}
