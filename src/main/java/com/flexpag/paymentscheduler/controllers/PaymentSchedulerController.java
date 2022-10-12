package com.flexpag.paymentscheduler.controllers;

import com.flexpag.paymentscheduler.services.PaymentSchedulerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment-scheduler")
public class PaymentSchedulerController {
    final PaymentSchedulerService paymentSchedulerService;

    public PaymentSchedulerController(PaymentSchedulerService paymentSchedulerService) {
        this.paymentSchedulerService = paymentSchedulerService;
    }

    @GetMapping
    public String hello() {
        return "Hello, World";
    }

}
