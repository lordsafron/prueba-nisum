package com.hm.pruebanisum.app.repository;

import com.hm.pruebanisum.app.config.DefaultJpaTestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @Sql(scripts = "/insert_user.sql")
    void findByEmail() {
        // given
        var email = "juan@dominio.cl";

        // when
        var result = userRepository.findByEmail(email);

        // then
        Assertions.assertTrue(result.isPresent());
    }
}