package com.flexpag.paymentscheduler.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime paymentSchedule;

    @NotNull(message = "O código de barra é obrigatório")
    @Size(max = 13, min = 13, message = "Digite um código de barras válido.")
    private String barCode;

    @NotNull(message = "O CPF é obrigatório")
    @Size(max = 11, min = 11, message = "Digite somente os números do CPF.")
    @CPF(message = "CPF inválido.")
    private String registrationNumber;
}
