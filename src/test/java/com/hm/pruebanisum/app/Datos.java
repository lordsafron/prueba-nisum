package com.hm.pruebanisum.app;

import com.hm.pruebanisum.app.models.dto.PhoneDto;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import com.hm.pruebanisum.app.models.entities.Usuario;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class Datos {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NmZiOWNkMi1lOGE2LTRiNGItODIzMi0xZjhkOTNmMGZhMGUiLCJpYXQiOjE2MzA3ODkzMDgsInN1YiI6Imp1YW5AZG9tYWluLmNsIiwiaXNzIjoiMTkyYjlhOGItOTFiMC00NDgyLTllMWEtZDYyOGY2OTg4ZDJjIiwiZXhwIjoxNjMwNzg5MzEyfQ.8IG0NNQNGhZn9-3QbOkk_vosBkbNNeNb7aY1_Lq7bAs";

    public static final UserRequestDto USER_REQUEST_DTO = UserRequestDto.builder()
            .email("email@dominio.cl")
            .name("name")
            .password("Password12")
            .phones(Collections.singletonList(PhoneDto.builder()
                    .citycode("01")
                    .contrycode("CO")
                    .number("1234332")
                    .build()))
            .build();

    public static final UserRequestDto USER_BAD_REQUEST_DTO = UserRequestDto.builder()
            .name("name")
            .password("Password12")
            .phones(Collections.singletonList(PhoneDto.builder()
                    .citycode("01")
                    .contrycode("CO")
                    .number("1234332")
                    .build()))
            .build();

    public static final UserRequestDto USER_REQUEST_DTO_UPDATE = UserRequestDto.builder()
            .email("email@dominio.cl")
            .name("nameUpdate")
            .password("Password12Up")
            .phones(Arrays.asList(PhoneDto.builder()
                    .citycode("01")
                    .contrycode("CO")
                    .number("1234332")
                    .build()))
            .build();

    public static final Usuario USUARIO_DISABLED = Usuario.builder()
            .password("Password12")
            .name("name")
            .email("email@dominio.cl")
            .lastLogin(LocalDateTime.now())
            .active(false)
            .build();

    public static final Usuario USUARIO_BD = Usuario.builder()
            .password("Passwordbd12")
            .name("nameBD")
            .email("emailbd@dominio.cl")
            .lastLogin(LocalDateTime.now())
            .id(UUID.randomUUID().toString())
            .active(true)
            .build();

    public static final Usuario USUARIO_BD_SAVE = Usuario.builder()
            .password("Passwordbd22")
            .name("nameBDSAVE")
            .email("emailbdsave@dominio.cl")
            .lastLogin(LocalDateTime.now())
            .id(UUID.randomUUID().toString())
            .active(true)
            .build();

    public static final Usuario USUARIO_UPDATE = Usuario.builder()
            .password("Passwordbd22")
            .name("nameBDSAVE")
            .email("emailbdsave@dominio.cl")
            .lastLogin(LocalDateTime.now())
            .id(UUID.randomUUID().toString())
            .active(true)
            .build();

    public static final OauthAccessToken OAUTH_ACCESS_TOKEN = OauthAccessToken.builder()
            .authenticationId(USUARIO_BD.getId())
            .tokenId(1L)
            .token(TOKEN)
            .build();

}
