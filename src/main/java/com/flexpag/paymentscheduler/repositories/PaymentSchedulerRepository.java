package com.flexpag.paymentscheduler.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;

@Repository
public interface PaymentSchedulerRepository extends JpaRepository<PaymentSchedulerModel, UUID> {

}
