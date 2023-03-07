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

	public List<PaymentModel> getByStatus(int status) {
		return paymentRepository.findByStatus(status);

	}

	public PaymentModel postSchedule(PaymentModel payment) {
		if (payment.getPaymentDate().isAfter(Instant.now())) {
			payment.setStatus(PaymentStatus.PENDING);
			return paymentRepository.save(payment);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public PaymentModel updateSchedule(Long id, PaymentModel payment) {
		Optional<PaymentModel> paymentId = paymentRepository.findById(id);
		if (paymentId.isPresent()) {
			PaymentModel paymentUpdate = paymentId.get();
			if (payment.getPaymentDate().isAfter(Instant.now())) {
				paymentUpdate.setPaymentDate(payment.getPaymentDate());
				return paymentRepository.save(paymentUpdate);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void deleteSchedule(Long id) {
		Optional<PaymentModel> paymentId = paymentRepository.findById(id);
		if (paymentId.isPresent()) {
			PaymentModel schedule = paymentId.get();
			if (schedule.getStatus() == PaymentStatus.PENDING) {
				paymentRepository.deleteById(id);
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

}
