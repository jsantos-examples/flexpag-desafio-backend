package com.flexpag.paymentscheduler.validations;

import com.flexpag.paymentscheduler.constraints.LocalDateTimeInterface;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeValidation implements ConstraintValidator<LocalDateTimeInterface, LocalDateTime>{


    @Override
    public void initialize(LocalDateTimeInterface constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {

        if (String.valueOf(localDateTime).matches("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2})$") == false) {
            return false;
        }

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
