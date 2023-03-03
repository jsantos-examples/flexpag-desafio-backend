package com.flexpag.paymentscheduler.model.enums;

public enum PaymentStatus {

    PENDING(false),
    PAID(true);

    private final boolean enumCode;

    private PaymentStatus(boolean enumCode) {
        this.enumCode = enumCode;
    }

    public boolean getEnumCode() {
        return enumCode;
    }

    public static PaymentStatus valueOf(boolean enumCode) {
        for (PaymentStatus value : PaymentStatus.values()) {
            if (value.getEnumCode() == enumCode) {
                return value;
            }
        }
        throw new IllegalArgumentException("Error: status code doesn't match with 'enumCode' values");
    }

}
