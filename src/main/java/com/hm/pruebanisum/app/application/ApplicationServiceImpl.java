package com.hm.pruebanisum.app.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.exceptions.BusinessException;
import com.hm.pruebanisum.app.mapper.UsuarioMapper;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import com.hm.pruebanisum.app.models.entities.Usuario;
import com.hm.pruebanisum.app.services.IOauthAccessTokenService;
import com.hm.pruebanisum.app.services.IUserService;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements IApplicationService {

    private final IUserService userService;
    private final IOauthAccessTokenService oauthAccessTokenService;
    private final JWTTokenManager jwtTokenManager;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) throws JsonProcessingException {
        Optional<Usuario> oUsuarioBD = userService.findByEmail(userRequestDto.getEmail());

        if (oUsuarioBD.isPresent()) {
            throw new BusinessException("El correo ya registrado");
        }

        var usuario =
                userService.save(UsuarioMapper.dtoToEntity(userRequestDto));

        var oauthAccessToken = oauthAccessTokenService.save(
                OauthAccessToken.builder()
                        .authenticationId(usuario.getId())
                        .token(jwtTokenManager.createJWT(UUID.randomUUID().toString(), usuario.getId(), usuario.getEmail()))
                        .build()
        );
        return UsuarioMapper.entityToDto(usuario, oauthAccessToken.getToken());
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String token) throws JsonProcessingException {
        Optional<Usuario> oUsuarioBD = userService.findByEmail(userRequestDto.getEmail());

        if (oUsuarioBD.isEmpty()) {
            throw new BusinessException(String.format("El Usuario %s no existe", userRequestDto.getEmail()));
        }

        if (!oUsuarioBD.get().isActive()) {
            throw new BusinessException(String.format("El usuario %s se encuentra inactivo", userRequestDto.getEmail()));
        }

        var usuarioUpdated =
                userService.save(UsuarioMapper.requestDtoToEntity(userRequestDto, oUsuarioBD.get()));

        return UsuarioMapper.entitiyUpdatedToDto(usuarioUpdated, token);
    }

    @Override
    @Transactional
    public UserResponseDto changeStatusUser(String email, String token) throws JsonProcessingException {
        Optional<Usuario> oUsuarioBD = userService.findByEmail(email);

        if (oUsuarioBD.isEmpty()) {
            throw new BusinessException(String.format("El Usuario %s no existe", email));
        }
        if (!oUsuarioBD.get().isActive()) {
            throw new BusinessException(String.format("El Usuario %s ya está inactivo", email));
        }
        oUsuarioBD.get().setActive(!oUsuarioBD.get().isActive());
        var usuarioUpdated = userService.save(oUsuarioBD.get());

        return UsuarioMapper.entitiyUpdatedToDto(usuarioUpdated, token);
    }

}
