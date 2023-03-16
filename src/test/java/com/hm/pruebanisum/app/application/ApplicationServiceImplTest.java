package com.hm.pruebanisum.app.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.exceptions.BusinessException;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.services.IOauthAccessTokenService;
import com.hm.pruebanisum.app.services.IUserService;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.hm.pruebanisum.app.Datos.OAUTH_ACCESS_TOKEN;
import static com.hm.pruebanisum.app.Datos.TOKEN;
import static com.hm.pruebanisum.app.Datos.USER_REQUEST_DTO;
import static com.hm.pruebanisum.app.Datos.USER_REQUEST_DTO_UPDATE;
import static com.hm.pruebanisum.app.Datos.USUARIO_BD;
import static com.hm.pruebanisum.app.Datos.USUARIO_BD_SAVE;
import static com.hm.pruebanisum.app.Datos.USUARIO_DISABLED;
import static com.hm.pruebanisum.app.Datos.USUARIO_UPDATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplicationServiceImplTest {

    IUserService userService;
    IOauthAccessTokenService oauthAccessTokenService;
    JWTTokenManager jwtTokenManager;

    IApplicationService applicationService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(IUserService.class);
        oauthAccessTokenService = Mockito.mock(IOauthAccessTokenService.class);
        jwtTokenManager = Mockito.mock(JWTTokenManager.class);
        applicationService = new ApplicationServiceImpl(userService, oauthAccessTokenService, jwtTokenManager);
    }

    @Test
    void testCreateUser() throws JsonProcessingException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userService.save(any())).thenReturn(USUARIO_BD_SAVE);
        when(jwtTokenManager.createJWT(anyString(), anyString(), anyString())).thenReturn(TOKEN);
        when(oauthAccessTokenService.save(any())).thenReturn(OAUTH_ACCESS_TOKEN);

        UserResponseDto userResponseDto = applicationService.createUser(USER_REQUEST_DTO);

        assertNotNull(userResponseDto);


        verify(userService, atLeastOnce()).findByEmail(anyString());
        verify(userService, atLeastOnce()).save(any());

    }

    @Test
    void testCreateUserWhenEmailUserExist() {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_BD));

        assertThrows(BusinessException.class, () -> applicationService.createUser(USER_REQUEST_DTO));

        verify(userService, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUser() throws JsonProcessingException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_BD));
        when(userService.save(any())).thenReturn(USUARIO_UPDATE);

        applicationService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN);

        verify(userService, atLeastOnce()).save(any());
        verify(userService, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUserWhenUserNotExist() throws JsonProcessingException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> applicationService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN));

        verify(userService, never()).save(any());
        verify(userService, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testUpdateUserWhenUserDisabled() throws JsonProcessingException {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_DISABLED));

        BusinessException businessException = assertThrows(BusinessException.class, () -> applicationService.updateUser(USER_REQUEST_DTO_UPDATE, TOKEN));

        assertTrue(businessException.getMessage().contains("inactivo"));

        verify(userService, never()).save(any());
        verify(userService, atLeastOnce()).findByEmail(anyString());

    }

    @Test
    void testChangeStatusUserWhenEmailUserExist() {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());

        var exception =
                Assertions.assertThrows(BusinessException.class, () -> applicationService.changeStatusUser("", ""));

        Assertions.assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    void testChangeStatusUserWhenUserDisabled() {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(USUARIO_DISABLED));

        var exception =
                Assertions.assertThrows(BusinessException.class, () -> applicationService.changeStatusUser("", ""));

        Assertions.assertTrue(exception.getMessage().contains("inactivo"));
    }
}