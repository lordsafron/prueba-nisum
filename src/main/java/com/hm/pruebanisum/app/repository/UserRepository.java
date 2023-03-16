package com.hm.pruebanisum.app.repository;

import com.hm.pruebanisum.app.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

}
