package com.hm.pruebanisum.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.Datos;
import com.hm.pruebanisum.app.config.BeanConfiguration;
import com.hm.pruebanisum.app.application.IApplicationService;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = BeanConfiguration.class)
class AppControllerTest {

    @MockBean
    IApplicationService userService;

    @Autowired MockMvc mvc;

    @Autowired ObjectMapper mapper;

    @MockBean JWTTokenManager jwtTokenManager;


    @Test
    void createUser() throws Exception {
        // given
        var request = Datos.USER_REQUEST_DTO;

        // when
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_BAD_REQUEST() throws Exception {
        // given
        var request = Datos.USER_BAD_REQUEST_DTO;

        // when
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser() throws Exception {
        // given
        var request = Datos.USER_REQUEST_DTO;
        var token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwN2E3MDc1ZS1mNmI4LTQ3MGItYmRjMy05MWMyMDEzYzA0MGUiLCJpYXQiOjE2Nzg5ODgyOTksInN1YiI6Imp1YW5AZG9taW5pby5jbCIsImlzcyI6ImYwZmQ3YzY1LTgwZGItNGY2ZS05Y2VkLTliY2E2Yzk5ZjZmZiIsImV4cCI6MTY3ODk5MTg5OX0.mMcd3NMp6GyLhH2VlhE_DiXInRMFcvyhep1u-GUjA0Q";
        when(jwtTokenManager.decodeJWT(token)).thenReturn(new DefaultClaims());

        // when
        mvc.perform(MockMvcRequestBuilders.put("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void changeStatusUser() throws Exception {
        // given
        var request = Datos.USER_REQUEST_DTO;
        var token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwN2E3MDc1ZS1mNmI4LTQ3MGItYmRjMy05MWMyMDEzYzA0MGUiLCJpYXQiOjE2Nzg5ODgyOTksInN1YiI6Imp1YW5AZG9taW5pby5jbCIsImlzcyI6ImYwZmQ3YzY1LTgwZGItNGY2ZS05Y2VkLTliY2E2Yzk5ZjZmZiIsImV4cCI6MTY3ODk5MTg5OX0.mMcd3NMp6GyLhH2VlhE_DiXInRMFcvyhep1u-GUjA0Q";
        when(jwtTokenManager.decodeJWT(token)).thenReturn(new DefaultClaims());

        // when
        mvc.perform(MockMvcRequestBuilders.put("/user/change")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}