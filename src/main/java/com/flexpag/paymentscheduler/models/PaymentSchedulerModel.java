package com.flexpag.paymentscheduler.models;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_PAYMENT_SCHEDULER")
public class PaymentSchedulerModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    
    @Column(nullable = false)
    private String namePayment;
    
    @Column(nullable = false)
    private LocalDateTime schedulingDate;
    
    @Column(nullable = false)
    private LocalDateTime created_at;
    
    @Column(nullable = false)
    private String status;

}
