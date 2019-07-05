package com.example.model;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by pf46pnd on 22/05/2019.
 */


public class Input {

    @Max(1000)
    @Min(value = 0L, message = "The value must be positive")
    @Valid
    public String number1;

    @Max(1000)
    @Min(value = 0L, message = "The value must be positive")
    public String number2;

    public Input(String number1, String number2) {
        this.number1 = number1;
        this.number2= number2;
    }

    public Input() {

    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }
}
