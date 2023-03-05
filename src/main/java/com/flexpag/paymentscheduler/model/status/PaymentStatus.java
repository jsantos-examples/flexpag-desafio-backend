package com.flexpag.paymentscheduler.model.status;

public enum PaymentStatus {
	
	PENDING(1),
	PAID(2);
	
	private int code;
	
	private PaymentStatus(int code) {
		this.code = code;
	}
	
	// Gettere setters

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	// static para acessar 
	public static PaymentStatus valueOf (int code) {
		for (PaymentStatus value : PaymentStatus.values()) {
			if (code == value.getCode()) {
				return value;
			}				
		}
		throw new IllegalArgumentException("Invalid Payment Status code");
	}
		
}
