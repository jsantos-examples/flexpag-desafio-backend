package com.flexpag.paymentscheduler.repositories;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentSchedulerRepository extends JpaRepository<PaymentSchedulerModel, UUID> {

}
