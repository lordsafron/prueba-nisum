package com.hm.pruebanisum.app.services;

import com.hm.pruebanisum.app.exceptions.BusinessException;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.repository.OauthAccessTokenRepository;
import com.hm.pruebanisum.app.repository.UserRepository;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;

import static com.hm.pruebanisum.app.Datos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    UserRepository userRepository;
    OauthAccessTokenRepository oauthAccessTokenRepository;
    JWTTokenManager jwtTokenManager;
    UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        oauthAccessTokenRepository = mock(OauthAccessTokenRepository.class);
        jwtTokenManager = mock(JWTTokenManager.class);
        userService = new UserServiceImpl(userRepository, oauthAccessTokenRepository, jwtTokenManager);
    }


    @Test
    void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(USUARIO_BD_SAVE);
        when(jwtTokenManager.createJWT(anyString(), anyString(), anyString())).thenReturn(TOKEN);
        when(oauthAccessTokenRepository.save(any())).thenReturn(OAUTH_ACCESS_TOKEN);

        UserResponseDto userResponseDto = userService.createUser(USER_REQUEST_DTO);

        assertNotNull(userResponseDto);


        verify(userRepository, atLeastOnce()).findByEmail(anyString());
        verify(userRepository, atLeastOnce()).save(any());

    }

    @Test
    void testCreateUserWhenEmailUserExist() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_BD));

        assertThrows(BusinessException.class, () -> userService.createUser(USER_REQUEST_DTO));

        verify(userRepository, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_BD));
        when(userRepository.save(any())).thenReturn(USUARIO_UPDATE);

        UserResponseDto userResponseDto = userService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN);

        assertTrue(Objects.nonNull(userResponseDto.getModified()));

        verify(userRepository, atLeastOnce()).save(any());
        verify(userRepository, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUserWhenUserNotExist() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN));

        verify(userRepository, never()).save(any());
        verify(userRepository, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUserWhenUserDisabled() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_DISABLED));

        BusinessException businessException = assertThrows(BusinessException.class, () -> userService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN));

        assertTrue(businessException.getMessage().contains("inactivo"));

        verify(userRepository, never()).save(any());
        verify(userRepository, atLeastOnce()).findByEmail(anyString());

    }

}