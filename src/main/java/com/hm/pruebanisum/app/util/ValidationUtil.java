package com.hm.pruebanisum.app.util;

import com.hm.pruebanisum.app.exceptions.BadRequestException;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationUtil {

    private ValidationUtil() {}

    public static void validateBody(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(
                    result.getFieldErrors()
                            .stream().map(err ->
                                    "El campo " + err.getField() + " " + err.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }
    }

}
