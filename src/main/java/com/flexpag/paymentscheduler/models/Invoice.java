package com.flexpag.paymentscheduler.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.flexpag.paymentscheduler.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Fatura")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_fatura")
    @Column(nullable = false)
    @SequenceGenerator(name = "sequence_fatura", sequenceName = "sequence_fatura", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(nullable = false, name = "valor")
    private Double value;

    @Column(name = "dt_pg_agenda")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime paymentSchedule;

    @Column(nullable = false, name = "dh_criacao")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dhCreation;

    @Column(nullable = false, name = "dh_modificacao")
    @Version
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dhModification;

    @Column(nullable = false, name = "dt_vencimento")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dhMaturity;

    @Column(nullable = false, name = "cod_bar", length = 13, unique = true)
    private String barcode;

    @Column(name = "status_fatura", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, targetEntity = User.class)
    @JoinColumn(name = "id_usuario")
    private User user;

}
