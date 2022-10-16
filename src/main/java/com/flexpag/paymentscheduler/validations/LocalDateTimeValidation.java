package com.flexpag.paymentscheduler.validations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;

public class LocalDateTimeValidation implements ConstraintValidator<LocalDateTimeInterface, LocalDateTime>{


    @Override
    public void initialize(LocalDateTimeInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {

        if (localDateTime == null) {
            return false;
        }

        var verifyDate = ChronoUnit.MINUTES.between(LocalDateTime.now(), localDateTime);

        if (verifyDate < 0) {
            return false;
        }

        return true;
    }
}
