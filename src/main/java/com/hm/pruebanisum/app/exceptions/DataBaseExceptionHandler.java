package com.hm.pruebanisum.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DataBaseExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> exceptionHandler(DataBaseException ex) {
        Map<String, String> responseErrorDto = new HashMap<>();
        responseErrorDto.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseErrorDto);
    }

}
