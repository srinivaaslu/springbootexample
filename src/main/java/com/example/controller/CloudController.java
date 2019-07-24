package com.example.controller;

import com.example.exception.InvalidInputParameter;
import com.example.model.ApiResponse;
import com.example.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CloudController {

    @Autowired
    private CloudService clouldService;


    @GetMapping("/cf")
    public ResponseEntity<List<ApiResponse>> getApiResponseFromCloud() {

        List<ApiResponse> apiResponses = clouldService.getAllApiResponseInfo();
        long errorCount = apiResponses.stream().filter(x->x.isError().equals("true")).count();
        final HttpStatus status = getStatusForOutput(errorCount);
        return new ResponseEntity(apiResponses,status);

    }

    private HttpStatus getStatusForOutput(long errorCount) {

        HttpStatus status =  HttpStatus.OK ;
        if(errorCount == 1){
            status = HttpStatus.PARTIAL_CONTENT ;
        }else if(errorCount > 1){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
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
