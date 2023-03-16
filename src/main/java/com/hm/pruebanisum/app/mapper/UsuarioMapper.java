package com.hm.pruebanisum.app.mapper;

import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.models.entities.Phone;
import com.hm.pruebanisum.app.models.entities.Usuario;

import java.util.stream.Collectors;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static Usuario dtoToEntity(UserRequestDto userRequestDto) {
        return Usuario.builder()
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .password(userRequestDto.getPassword())
                .phones(userRequestDto.getPhones().stream()
                        .map(dto -> Phone.builder()
                                .contryCode(dto.getContrycode())
                                .number(dto.getNumber())
                                .cityCode(dto.getCitycode())
                                .build()).collect(Collectors.toList())
                )
                .active(true)
                .build();
    }

    public static UserResponseDto entityToDto(Usuario usuario, String token) {
        return UserResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreatedAt())
                .modified(usuario.getUpdateAt())
                .lastLogin(usuario.getLastLogin())
                .token(token)
                .active(usuario.isActive())
                .build();
    }

    public static Usuario requestDtoToEntity(UserRequestDto userRequestDto, Usuario usuario) {
        usuario.setName(userRequestDto.getName());
        usuario.setPassword(userRequestDto.getPassword());
        usuario.addPhones(userRequestDto.getPhones().stream()
                .map(dto -> Phone.builder()
                        .cityCode(dto.getCitycode())
                        .number(dto.getNumber())
                        .contryCode(dto.getContrycode())
                        .build())
                .collect(Collectors.toList()));
        return usuario;
    }

    public static UserResponseDto entitiyUpdatedToDto(Usuario usuario, String token) {
        return UserResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreatedAt())
                .modified(usuario.getUpdateAt())
                .token(token)
                .lastLogin(usuario.getLastLogin())
                .active(usuario.isActive())
                .modified(usuario.getUpdateAt())
                .build();
    }

}
