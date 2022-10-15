package com.flexpag.paymentscheduler.dtos;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
public class PaymentSchedulerDto {

    @NotBlank(message = "Empty field")
    private String namePayment;

    @LocalDateTimeInterface
    private LocalDateTime schedulingDate;

}