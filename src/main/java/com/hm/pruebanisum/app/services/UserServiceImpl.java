package com.hm.pruebanisum.app.services;

import com.hm.pruebanisum.app.exceptions.BusinessException;
import com.hm.pruebanisum.app.models.dto.UserRequestDto;
import com.hm.pruebanisum.app.models.dto.UserResponseDto;
import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import com.hm.pruebanisum.app.models.entities.Phone;
import com.hm.pruebanisum.app.models.entities.Usuario;
import com.hm.pruebanisum.app.repository.OauthAccessTokenRepository;
import com.hm.pruebanisum.app.repository.UserRepository;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OauthAccessTokenRepository oauthAccessTokenRepository;
    private final JWTTokenManager jwtTokenManager;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<Usuario> oUsuarioBD = userRepository.findByEmail(userRequestDto.getEmail());

        if (oUsuarioBD.isPresent()) {
            throw new BusinessException("El correo ya registrado");
        }

        Usuario usuario = userRepository.save(
                Usuario.builder()
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .password(userRequestDto.getPassword())
                .phones(userRequestDto.getPhones().stream()
                .map(dto -> Phone.builder()
                        .contryCode(dto.getContrycode())
                        .number(dto.getNumber())
                        .cityCode(dto.getCitycode())
                        .build()).collect(Collectors.toList())
                )
                .build()
        );

        OauthAccessToken oauthAccessToken = oauthAccessTokenRepository.save(
                OauthAccessToken.builder()
                        .authenticationId(usuario.getId())
                        .token(jwtTokenManager.createJWT(UUID.randomUUID().toString(), usuario.getId(), usuario.getEmail()))
                        .build()
        );
        return UserResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreated())
                .modified(usuario.getModified())
                .lastLogin(usuario.getLastLogin())
                .token(oauthAccessToken.getToken())
                .active(usuario.isActive())
                .build();
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String token) {
        Optional<Usuario> oUsuarioBD = userRepository.findByEmail(userRequestDto.getEmail());

        if (!oUsuarioBD.isPresent()) {
            throw new BusinessException(String.format("El Usuario %s no existe", userRequestDto.getEmail()));
        }

        if (!oUsuarioBD.get().isActive()) {
            throw new BusinessException(String.format("El usuario %s se encuentra inactivo", userRequestDto.getEmail()));
        }

        Usuario usuarioNew = oUsuarioBD.get();
        usuarioNew.setName(userRequestDto.getName());
        usuarioNew.setPassword(userRequestDto.getPassword());
        usuarioNew.setModified(LocalDateTime.now());
        usuarioNew.addPhones(userRequestDto.getPhones().stream()
        .map(dto -> Phone.builder()
                .cityCode(dto.getCitycode())
                .number(dto.getNumber())
                .contryCode(dto.getContrycode())
                .build())
        .collect(Collectors.toList()));
        Usuario usuarioUpdated = userRepository.save(usuarioNew);

        return UserResponseDto.builder()
                .id(usuarioUpdated.getId())
                .token(token)
                .created(usuarioUpdated.getCreated())
                .modified(usuarioUpdated.getModified())
                .lastLogin(usuarioUpdated.getLastLogin())
                .active(usuarioUpdated.isActive())
                .build();
    }

    @Override
    @Transactional
    public UserResponseDto changeStatusUser(String email, String token) {
        Optional<Usuario> oUsuarioBD = userRepository.findByEmail(email);

        if (!oUsuarioBD.isPresent()) {
            throw new BusinessException(String.format("El Usuario %s no existe", email));
        }
        oUsuarioBD.get().setActive(oUsuarioBD.get().isActive() ? false : true);
        Usuario usuarioUpdated = userRepository.save(oUsuarioBD.get());

        return UserResponseDto.builder()
                .id(usuarioUpdated.getId())
                .token(token)
                .created(usuarioUpdated.getCreated())
                .modified(usuarioUpdated.getModified())
                .lastLogin(usuarioUpdated.getLastLogin())
                .active(usuarioUpdated.isActive())
                .build();
    }

}
