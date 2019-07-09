package com.example.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveOrZeroValidatorForNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by pf46pnd on 9/07/2019.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidatorForNumber.class)
public @interface PositiveNumber {

    String message() default "Value should be positive number and less than or equal to 1000";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
