package com.hm.pruebanisum.app.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;

public interface IApplicationService {

    UserResponseDto createUser(UserRequestDto userRequestDto) throws JsonProcessingException;

    UserResponseDto updateUser(UserRequestDto userRequestDto, String token) throws JsonProcessingException;

    UserResponseDto changeStatusUser(String email, String token) throws JsonProcessingException;
}
