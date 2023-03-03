package com.flexpag.paymentscheduler.model;

import com.flexpag.paymentscheduler.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_scheduling")
public class PaymentScheduling implements Serializable {

    //Permitindo a serialização da classe
    private static final long serialVersionUID = 1L;

    //Criação das colunas do BD seguindo as regras de negócio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //timestamp
    private LocalDateTime schedulingDateTime;

    public PaymentScheduling(LocalDateTime schedulingDateTime, Double amount) {
        this.status = PaymentStatus.PENDING.getEnumCode();
        this.amount = amount;
        this.schedulingDateTime = schedulingDateTime;
    }

}
