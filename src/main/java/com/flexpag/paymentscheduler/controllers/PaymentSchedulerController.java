package com.flexpag.paymentscheduler.controllers;

import com.flexpag.paymentscheduler.dtos.PaymentSchedulerDto;
import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.services.PaymentSchedulerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment-scheduler")
public class PaymentSchedulerController {
    final PaymentSchedulerService paymentSchedulerService;

    public PaymentSchedulerController(PaymentSchedulerService paymentSchedulerService) {
        this.paymentSchedulerService = paymentSchedulerService;
    }

    @PostMapping
    public ResponseEntity<Object> savePayment(@RequestBody @Valid PaymentSchedulerDto paymentSchedulerDto) {
        var verifyDate = ChronoUnit.MINUTES.between(LocalDateTime.now(), paymentSchedulerDto.getSchedulingDate());

        if (verifyDate < 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("invalid scheduling date");
        }

        var paymentSchedulerModel = new PaymentSchedulerModel();
        BeanUtils.copyProperties(paymentSchedulerDto, paymentSchedulerModel);
        paymentSchedulerModel.setCreated_at(LocalDateTime.now());
        paymentSchedulerModel.setStatus("pending");
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentSchedulerService.save(paymentSchedulerModel));
    }

    @GetMapping
    public ResponseEntity<List<PaymentSchedulerModel>> getAllPaymentScheduler() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentSchedulerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePaymentScheduler(@PathVariable(value = "id") UUID id) {
        Optional<PaymentSchedulerModel> paymentSchedulerModelOptional = paymentSchedulerService.findById(id);
        if (!paymentSchedulerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scheduling not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(paymentSchedulerModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaymentScheduler(@PathVariable(value = "id") UUID id) {
        Optional<PaymentSchedulerModel> paymentSchedulerModelOptional = paymentSchedulerService.findById(id);
        if (!paymentSchedulerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scheduling not found");
        }
        if (paymentSchedulerModelOptional.get().getStatus() == "paid") {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment has already been made");
        }
        paymentSchedulerService.delete(paymentSchedulerModelOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePaymentScheduler() {
        Optional<PaymentSchedulerModel> paymentSchedulerModelOptional = paymentSchedulerService.findById(id);
        if (!paymentSchedulerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scheduling not found");
        }
    }

}
