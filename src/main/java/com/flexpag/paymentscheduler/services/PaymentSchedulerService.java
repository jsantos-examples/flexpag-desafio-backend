package com.flexpag.paymentscheduler.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.repositories.PaymentSchedulerRepository;

@Service
public class PaymentSchedulerService {

    final PaymentSchedulerRepository paymentSchedulerRepository;

    public PaymentSchedulerService(PaymentSchedulerRepository paymentSchedulerRepository) {
        this.paymentSchedulerRepository = paymentSchedulerRepository;
    }

    @Transactional
    public UUID save(PaymentSchedulerModel paymentSchedulerModel) {
        paymentSchedulerRepository.save(paymentSchedulerModel);
        return paymentSchedulerModel.getId();
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
