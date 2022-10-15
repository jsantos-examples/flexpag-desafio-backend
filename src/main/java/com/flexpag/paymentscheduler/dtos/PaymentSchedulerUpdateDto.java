package com.flexpag.paymentscheduler.dtos;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentSchedulerUpdateDto {

    @LocalDateTimeInterface
    private LocalDateTime schedulingDate;
}
