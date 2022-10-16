package com.flexpag.paymentscheduler.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flexpag.paymentscheduler.dtos.*;
import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.services.PaymentSchedulerService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment-scheduler")
public class PaymentSchedulerController {
    final PaymentSchedulerService paymentSchedulerService;

    public PaymentSchedulerController(PaymentSchedulerService paymentSchedulerService) {
        this.paymentSchedulerService = paymentSchedulerService;
    }

    @PostMapping
    public ResponseEntity<UUID> savePayment(@RequestBody @Valid PaymentSchedulerDto paymentSchedulerDto) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment has already been made");
        }
        paymentSchedulerService.delete(paymentSchedulerModelOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePaymentScheduler(@PathVariable(value = "id") UUID id, @RequestBody @Valid PaymentSchedulerDto paymentSchedulerDto) {
        Optional<PaymentSchedulerModel> paymentSchedulerModelOptional = paymentSchedulerService.findById(id);
        if (!paymentSchedulerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scheduling not found");
        }

        if (paymentSchedulerModelOptional.get().getStatus() == "paid") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment has already been made");
        }

        var paymentSchedulerModel = paymentSchedulerModelOptional.get();
        paymentSchedulerModel.setSchedulingDate(paymentSchedulerDto.getSchedulingDate());
        return ResponseEntity.status(HttpStatus.OK).body(paymentSchedulerService.update(paymentSchedulerModel));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException (MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.print(errorMessage);

            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
