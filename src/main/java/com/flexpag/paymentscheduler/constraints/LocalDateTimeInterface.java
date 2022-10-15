package com.flexpag.paymentscheduler.constraints;

import com.flexpag.paymentscheduler.validations.LocalDateTimeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateTimeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalDateTimeInterface {

    String message() default "Invalid scheduling date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
