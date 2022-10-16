package com.flexpag.paymentscheduler.dtos;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@ApiModel
public class PaymentSchedulerDto {

    @ApiModelProperty(name = "namePayment", position = 1, example = "Name payment")
    @NotBlank(message = "Empty field")
    private String namePayment;

    @LocalDateTimeInterface
    @ApiModelProperty(name = "schedulingDate", position = 2, example = "2122-10-16T15:58:09.644Z")
    private LocalDateTime schedulingDate;

}
