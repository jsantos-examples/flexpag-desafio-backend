package com.flexpag.paymentscheduler.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flexpag.paymentscheduler.model.PaymentModel;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {
    public List<PaymentModel> findByStatus(int status);

}