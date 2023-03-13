package com.flexpag.paymentscheduler.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flexpag.paymentscheduler.status.Status;


import lombok.NonNull;


public class StatusPaymentsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	private Status status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
	private LocalDateTime date;
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public StatusPaymentsDto(Long id, @NonNull Status status) {
		super();
		this.id = id;
		this.status = status;
		
	}

	public StatusPaymentsDto(Long id, @NonNull Status status, LocalDateTime date) {
		this.id = id;
		this.status = status;
		this.date = date;
	}

}
