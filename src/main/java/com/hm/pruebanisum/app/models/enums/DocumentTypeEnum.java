package com.hm.pruebanisum.app.models.enums;

import com.hm.pruebanisum.app.exceptions.BadRequestException;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public enum DocumentTypeEnum {

    CC,
    CE,
    PA,
    UNKNOWN;

    public static DocumentTypeEnum fromString(String value) {
        return List.of(values())
                .stream()
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                //En caso que sea optional
                //.orElseGet(() -> UNKNOWN);
                // En caso que se deba retornar un error
                .orElseThrow(() -> new BadRequestException("documentType no permitido"));
    }

}
