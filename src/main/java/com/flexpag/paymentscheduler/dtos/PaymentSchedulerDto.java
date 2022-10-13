package com.flexpag.paymentscheduler.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PaymentSchedulerDto {

    @NotBlank
    private String namePayment;

    @NotBlank
    private LocalDateTime schedulingDate;
}
