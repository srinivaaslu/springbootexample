package com.example.controller;

import com.example.model.Output;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pf46pnd on 22/05/2019.
 */
@RestController
public class AdditionResource {

    @PostMapping("/sum")
    public Output addNumber(){

        Output output = new Output();
        return output;
    }

}
