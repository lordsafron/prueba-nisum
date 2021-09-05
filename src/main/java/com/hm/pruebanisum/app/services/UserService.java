package com.hm.pruebanisum.app.services;

import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UserRequestDto userRequestDto, String token);

    UserResponseDto changeStatusUser(String email, String token);
}
