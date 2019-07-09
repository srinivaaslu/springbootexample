package com.example.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by pf46pnd on 9/07/2019.
 */

public class ValidatorForNumber implements ConstraintValidator<PositiveNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (StringUtils.hasLength(value)) {
            try {
                return Integer.parseInt(value) > 0 && Integer.parseInt(value) <= 1000;
            } catch (NumberFormatException ex) {
                throw new NumberFormatException();
            }
        } else {
            return true;
        }

    }

}
