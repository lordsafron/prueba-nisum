package com.hm.pruebanisum.app.models.dto;

import lombok.*;

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

}
