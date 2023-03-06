package com.flexpag.paymentscheduler.models;

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
@Table(name = "USUARIO")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_usuario")
    @Column(nullable = false)
    @SequenceGenerator(name = "sequence_usuario", sequenceName = "sequence_usuario", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(nullable = false, length = 130, name = "nome")
    private String name;

    @Column(nullable = false, unique = true, length = 11, name = "cpf")
    private String registrationNumber;

    @Column(nullable = false, name = "dh_criacao")
    private LocalDateTime dhCreation;

    @Column(nullable = false, name = "dh_modificacao")
    @Version
    private LocalDateTime dhModification;
}
