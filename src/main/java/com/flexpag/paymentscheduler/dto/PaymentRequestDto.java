package com.flexpag.paymentscheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"paymentStatus"})
public class PaymentRequestDto extends PaymentDto {
}