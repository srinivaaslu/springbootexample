package com.example.controller;

import com.example.model.Input;
import com.example.model.Output;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
public class AdditionController {

    @PostMapping("/sum")
    public Output addNumbers(@Valid @RequestBody Input input){
        int result ;

        result = addGivenIntegers(input);

        Output output = new Output();
        output.setResult(String.valueOf(result));
        return output;
    }

    private int addGivenIntegers(@RequestBody Input input) {
        int result;
//        try {
            result = (StringUtils.hasLength(input.getNumber1())? Integer.parseInt(input.getNumber1()) :0 ) + (StringUtils.hasLength(input.getNumber2()) ? Integer.parseInt(input.getNumber2()) :0);
//        }catch (NumberFormatException ex){
//            throw new NumberFormatException();
//        }
        return result;
    }

}
