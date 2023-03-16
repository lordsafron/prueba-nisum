package com.hm.pruebanisum.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.application.IApplicationService;
import com.hm.pruebanisum.app.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class AppController {

    @Autowired
    private IApplicationService appService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto userRequestDto, BindingResult result
    ) throws JsonProcessingException {
        ValidationUtil.validateBody(result);
        return ResponseEntity.ok(appService.createUser(userRequestDto));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody UserRequestDto userRequestDto, BindingResult result,
            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        ValidationUtil.validateBody(result);
        return ResponseEntity.ok(appService.updateUser(userRequestDto, token));
    }

    @PutMapping("/change")
    public ResponseEntity<UserResponseDto> changeStatusUser(
            @Valid @RequestBody UserRequestDto userRequestDto, BindingResult result,
            @RequestHeader("Authorization") String token
    ) throws JsonProcessingException {
        ValidationUtil.validateBody(result);
        return ResponseEntity.ok(appService.changeStatusUser(userRequestDto.getEmail(), token));
    }

}
