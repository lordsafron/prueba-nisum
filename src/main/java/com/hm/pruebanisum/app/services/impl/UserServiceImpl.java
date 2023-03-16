package com.hm.pruebanisum.app.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.exceptions.DataBaseException;
import com.hm.pruebanisum.app.models.entities.Usuario;
import com.hm.pruebanisum.app.repository.UserRepository;
import com.hm.pruebanisum.app.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;

    private final ObjectMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        try {
            return repository.findByEmail(email);
        } catch (Exception ex) {
            log.error("UserServiceImpl::findByEmail --email [{}] --exCause [{}] --exMessage [{}]", email, ex.getCause(), ex.getMessage());
            throw new DataBaseException("problemas con el banco de datos");
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) throws JsonProcessingException {
        try {
            return repository.save(usuario);
        } catch (Exception ex) {
            log.error("UserServiceImpl::findByEmail --entity [{}] --exCause [{}] --exMessage [{}]", mapper.writeValueAsString(usuario), ex.getCause(), ex.getMessage());
            throw new DataBaseException("problemas con el banco de datos");
        }
    }
}
