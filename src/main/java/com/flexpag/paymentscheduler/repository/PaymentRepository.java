package com.flexpag.paymentscheduler.repository;

import com.flexpag.paymentscheduler.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}