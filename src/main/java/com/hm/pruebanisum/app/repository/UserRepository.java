package com.hm.pruebanisum.app.repository;

import com.hm.pruebanisum.app.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

}
