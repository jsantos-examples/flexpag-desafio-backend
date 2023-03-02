package com.flexpag.paymentscheduler.mapper;

import com.flexpag.paymentscheduler.dto.PaymentDto;
import com.flexpag.paymentscheduler.dto.PaymentRequestDto;
import com.flexpag.paymentscheduler.entity.Payment;
import com.flexpag.paymentscheduler.entity.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment mapToPayment(PaymentRequestDto paymentRequestDto) {
        return Payment.builder()
                .client(paymentRequestDto.getClient())
                .amount(paymentRequestDto.getAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .payDate(paymentRequestDto.getPayDate())
                .build();
    }

    public PaymentDto mapToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .client(payment.getClient())
                .amount(payment.getAmount())
                .paymentStatus(payment.getPaymentStatus())
                .payDate(payment.getPayDate())
                .build();
    }

}