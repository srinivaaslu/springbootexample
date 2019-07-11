package com.example.model;

import com.example.validator.PositiveNumber;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



public class Input {

    @PositiveNumber
    @Valid
    public String number1;

    @PositiveNumber
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
