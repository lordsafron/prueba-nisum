package com.hm.pruebanisum.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.models.entities.Usuario;

import java.util.Optional;

public interface IUserService {


    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario) throws JsonProcessingException;
}
