package com.hm.pruebanisum.app.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class JsonProcessingExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<Map<String, String>> exceptionHandler(JsonProcessingException ex) {
        Map<String, String> responseErrorDto = new HashMap<>();
        responseErrorDto.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseErrorDto);
    }
}
