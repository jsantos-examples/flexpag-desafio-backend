package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDto> getAllPayments() {
        return paymentService.findAllPayments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto checkPayment(@PathVariable Long id) {
        return paymentService.checkPayment(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.save(paymentRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDto updatePayDate(@PathVariable Long id, @RequestParam LocalDateTime localDateTime) {
        return paymentService.updatePayDate(id, localDateTime);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
    }

}