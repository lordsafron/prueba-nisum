package com.hm.pruebanisum.app.models.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hm.pruebanisum.app.models.enums.DocumentTypeDeserialize;
import com.hm.pruebanisum.app.models.enums.DocumentTypeEnum;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String name;
    @Pattern(regexp = "^(.+)@(.+)$", message = "formato incorrecto")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]{2})[A-Za-z\\d]{8,}$", message = "formato incorrecto")
    private String password;
    private List<PhoneDto> phones;
    @NotNull
    @JsonDeserialize(using = DocumentTypeDeserialize.class)
    private DocumentTypeEnum documentType;

}
