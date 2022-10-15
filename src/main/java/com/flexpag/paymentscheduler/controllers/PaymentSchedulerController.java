package com.flexpag.paymentscheduler.controllers;

import com.flexpag.paymentscheduler.dtos.PaymentSchedulerDto;
import com.flexpag.paymentscheduler.dtos.PaymentSchedulerUpdateDto;
import com.flexpag.paymentscheduler.models.PaymentSchedulerModel;
import com.flexpag.paymentscheduler.services.PaymentSchedulerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

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
    public ResponseEntity<Object> updatePaymentScheduler(@PathVariable(value = "id") UUID id, @RequestBody @Valid PaymentSchedulerUpdateDto paymentSchedulerDto) {
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