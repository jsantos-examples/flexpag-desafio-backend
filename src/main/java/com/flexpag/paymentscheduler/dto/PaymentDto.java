package com.flexpag.paymentscheduler.dto;

import com.flexpag.paymentscheduler.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;
    private String client;
    private Double amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime payDate;

}