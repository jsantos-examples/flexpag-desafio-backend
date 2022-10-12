package com.flexpag.paymentscheduler.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PAYMENT_SCHEDULER")
@Data
public class PaymentSchedulerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String namePayment;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

}
