package com.mazhar.blogs.app.exceptions;

import com.mazhar.blogs.app.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException rnfe){
        String msg= rnfe.getMessage();
        ApiResponse apiResponse = new ApiResponse(msg,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validateData(MethodArgumentNotValidException ex){
       Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
           String field = ((FieldError)error).getField();
           String value = error.getDefaultMessage();
           resp.put(field,value);
        });
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException apiEx){
        String msg= apiEx.getMessage();
        ApiResponse apiResponse = new ApiResponse(msg,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
