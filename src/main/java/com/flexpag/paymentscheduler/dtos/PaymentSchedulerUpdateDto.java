package com.flexpag.paymentscheduler.dtos;

import java.time.LocalDateTime;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;

import lombok.Data;

@Data
public class PaymentSchedulerUpdateDto {

    @LocalDateTimeInterface
    private LocalDateTime schedulingDate;
}
