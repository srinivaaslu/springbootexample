package com.example.controller;

import com.example.exception.InvalidInputParameter;
import com.example.model.ApiResponse;
import com.example.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CloudController {

    @Autowired
    private CloudService clouldService;


    @GetMapping("/cf")
    public List<ApiResponse> getApiResponseFromCloud() {

            return clouldService.getAllApiResponseInfo();

    }


    @GetMapping("/cf/{code}")
    public ApiResponse getParticularApiResponseFromCloud(@PathVariable String code) {

        validatePathParam(code);
        return clouldService.getSpecificApiResponseInfo(code);

    }


    private void validatePathParam(String code) {
        String pattern = "^(?i)\\bPWC\\b|(?i)\\bBLU\\b$";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(code);

       if(!m.find()){
            throw new InvalidInputParameter("Invalid path parameter found");
       }
    }

}
