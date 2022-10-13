package com.flexpag.paymentscheduler.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.flexpag.paymentscheduler.repositories.PaymentSchedulerRepository;
import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;

@Service
public class PaymentSchedulerService {

    final PaymentSchedulerRepository paymentSchedulerRepository;

    public PaymentSchedulerService(PaymentSchedulerRepository paymentSchedulerRepository) {
        this.paymentSchedulerRepository = paymentSchedulerRepository;
    }

    @Transactional
    public UUID save(PaymentSchedulerModel paymentSchedulerModel) {
        var result = paymentSchedulerRepository.save(paymentSchedulerModel);
        return result.getId();
    }

    public List<PaymentSchedulerModel> findAll() {
        return paymentSchedulerRepository.findAll();
    }

    public Optional<PaymentSchedulerModel> findById(UUID id) {
        System.out.print(id);
        return paymentSchedulerRepository.findById(id);
    }

    public void delete(PaymentSchedulerModel paymentSchedulerModel) {
        paymentSchedulerRepository.delete(paymentSchedulerModel);
    }

    
}
