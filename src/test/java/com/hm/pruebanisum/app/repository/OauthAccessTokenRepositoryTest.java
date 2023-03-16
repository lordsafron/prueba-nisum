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
class OauthAccessTokenRepositoryTest {

    @Autowired OauthAccessTokenRepository oauthAccessTokenRepository;

    @Test
    @Sql(scripts = "/insert_oauth_token.sql")
    void findByToken() {
        // given
        var token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwN2E3MDc1ZS1mNmI4LTQ3MGItYmRjMy05MWMyMDEzYzA0MGUiLCJpYXQiOjE2Nzg5ODgyOTksInN1YiI6Imp1YW5AZG9taW5pby5jbCIsImlzcyI6ImYwZmQ3YzY1LTgwZGItNGY2ZS05Y2VkLTliY2E2Yzk5ZjZmZiIsImV4cCI6MTY3ODk5MTg5OX0.mMcd3NMp6GyLhH2VlhE_DiXInRMFcvyhep1u-GUjA0Q";

        // when
        var result = oauthAccessTokenRepository.findByToken(token);

        // then
        Assertions.assertNotNull(result);

    }
}