package com.hm.pruebanisum.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String name;
    @NotNull
    @Pattern(regexp = "^(.+)@(.+)$", message = "formato incorrecto")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]{2})[A-Za-z\\d]{8,}$", message = "formato incorrecto")
    private String password;
    private List<PhoneDto> phones;

}
