package com.flexpag.paymentscheduler.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.flexpag.paymentscheduler.validations.LocalDateTimeValidation;

@Documented
@Constraint(validatedBy = LocalDateTimeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateTimeInterface {

    String message() default "Invalid scheduling date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
