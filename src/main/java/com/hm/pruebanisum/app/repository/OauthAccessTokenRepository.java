package com.hm.pruebanisum.app.repository;

import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, Long> {

    OauthAccessToken findByToken(String token);

}
