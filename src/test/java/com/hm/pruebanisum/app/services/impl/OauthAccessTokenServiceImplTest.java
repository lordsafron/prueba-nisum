package com.hm.pruebanisum.app.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.Datos;
import com.hm.pruebanisum.app.exceptions.DataBaseException;
import com.hm.pruebanisum.app.repository.OauthAccessTokenRepository;
import com.hm.pruebanisum.app.services.IOauthAccessTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OauthAccessTokenServiceImplTest {

    OauthAccessTokenRepository repository;

    ObjectMapper mapper;

    IOauthAccessTokenService service;

    @BeforeEach
    void setUp() {
        repository = mock(OauthAccessTokenRepository.class);
        mapper = mock(ObjectMapper.class);
        service = new OauthAccessTokenServiceImpl(repository, mapper);
    }

    @Test
    void save() throws JsonProcessingException {
        // given
        var entity = Datos.OAUTH_ACCESS_TOKEN;
        when(repository.save(entity))
                .thenReturn(Datos.OAUTH_ACCESS_TOKEN);

        var result = service.save(entity);

        Assertions.assertNotNull(result);
    }

    @Test
    void save_throwException() throws JsonProcessingException {
        // given
        var entity = Datos.OAUTH_ACCESS_TOKEN;
        when(repository.save(entity))
                .thenThrow(DataBaseException.class);
        when(mapper.writeValueAsString(entity))
                .thenReturn("{}");

        var exception =
                Assertions.assertThrows(DataBaseException.class, () -> service.save(entity));

        Assertions.assertTrue(exception.getMessage().contains("banco de datos"));
    }
}