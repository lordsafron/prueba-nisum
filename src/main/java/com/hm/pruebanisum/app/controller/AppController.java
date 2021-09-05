package com.hm.pruebanisum.app.controller;

import com.hm.pruebanisum.app.exceptions.BadRequestException;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class AppController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            validar(result);
        }

        return ResponseEntity.ok(userService.createUser(userRequestDto));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody UserRequestDto userRequestDto, BindingResult result,
            @RequestHeader("Authorization") String token) {
        if (result.hasErrors()) {
            validar(result);
        }
        return ResponseEntity.ok(userService.updateUser(userRequestDto, token));
    }

    @PutMapping("/change")
    public ResponseEntity<UserResponseDto> changeStatusUser(
            @Valid @RequestBody UserRequestDto userRequestDto, BindingResult result,
            @RequestHeader("Authorization") String token
    ) {
        if (result.hasErrors()) {
            validar(result);
        }
        return ResponseEntity.ok(userService.changeStatusUser(userRequestDto.getEmail(), token));
    }

    private ResponseEntity<UserResponseDto> validar(BindingResult result) {
        throw new BadRequestException(
                result.getFieldErrors()
                        .stream().map(err ->
                        "El campo " + err.getField() + " " + err.getDefaultMessage())
                        .collect(Collectors.joining(", "))
        );
    }

}
