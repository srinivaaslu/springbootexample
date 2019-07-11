package com.example.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<Object>  handleNumberFormatException(NumberFormatException exception, WebRequest webRequest){
        List<String> details = new ArrayList<>();
        details.add("Invalid input number");
        ErrorResponse errorResponse = new ErrorResponse("Number Format Exception",details);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProcessingException.class)
    public final ResponseEntity<Object>  handleNumberFormatException(ProcessingException exception){
        List<String> details = new ArrayList<>();
        details.add(exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Json Parse exception happend",details);
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RestClientException.class)
    public final ResponseEntity<Object>  handleRestClientException(RestClientException exception){
        ErrorDetails errorResponse = new ErrorDetails("ERROR",exception.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputParameter.class)
    public final ResponseEntity<Object>  handleInvalidInputParameter(InvalidInputParameter exception){
        ErrorDetails errorResponse = new ErrorDetails("ERROR",exception.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

