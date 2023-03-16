package com.hm.pruebanisum.app.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.config.DefaultJpaTestConfiguration;
import com.hm.pruebanisum.app.exceptions.DataBaseException;
import com.hm.pruebanisum.app.repository.UserRepository;
import com.hm.pruebanisum.app.services.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.hm.pruebanisum.app.Datos.USUARIO_BD;
import static com.hm.pruebanisum.app.Datos.USUARIO_BD_SAVE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class UserServiceImplTest {

    UserRepository userRepository;

    ObjectMapper mapper;
    IUserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        mapper = mock(ObjectMapper.class);
        userService = new UserServiceImpl(userRepository, mapper);
    }


    @Test
    void findByEmail() {
        // given
        var email = "juan@dominio.cl";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(USUARIO_BD));

        // when
        var result = userService.findByEmail(email);

        // then
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void findByEmail_throwException() {
        // given
        var email = "juan@dominio.cl";
        when(userRepository.findByEmail(email))
                .thenThrow(DataBaseException.class);

        // when
        var exception =
                Assertions.assertThrows(DataBaseException.class, () -> userService.findByEmail(email));

        Assertions.assertTrue(exception.getMessage().contains("banco de datos"));
    }

    @Test
    void save() throws JsonProcessingException {
        // given
        var entity = USUARIO_BD;
        when(userRepository.save(entity))
                .thenReturn(USUARIO_BD_SAVE);
        // when
        var result = userService.save(entity);

        // then
        Assertions.assertNotNull(result);
    }

    @Test
    void save_throwException() throws JsonProcessingException {
        // given
        var entity = USUARIO_BD;
        when(userRepository.save(entity))
                .thenThrow(DataBaseException.class);
        when(mapper.writeValueAsString(entity))
                .thenReturn("{}");
        // when
        var exception = Assertions.assertThrows(DataBaseException.class, () -> userService.save(entity));

        // then
        Assertions.assertTrue(exception.getMessage().contains("banco de datos"));
    }

}