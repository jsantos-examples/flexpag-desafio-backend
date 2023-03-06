package com.flexpag.paymentscheduler.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexpag.paymentscheduler.model.PaymentModel;
import com.flexpag.paymentscheduler.model.status.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {
		public List<PaymentModel> findAllById (Long id);
		public List<PaymentModel> findByStatus(PaymentStatus status);

}