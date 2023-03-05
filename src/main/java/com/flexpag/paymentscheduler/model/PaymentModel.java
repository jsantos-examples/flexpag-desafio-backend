package com.flexpag.paymentscheduler.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.flexpag.paymentscheduler.model.status.PaymentStatus;

@Entity
@Table(name = "tb_payment")
public class PaymentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Instant paymentDate;

	private Integer status;

	
	// Getters e Setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Instant getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Instant paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentStatus getStatus() {
		return PaymentStatus.valueOf(status);
	}

	public void setStatus(PaymentStatus status) {
		if (status != null) {
			this.status = status.getCode();
		}

	}

}