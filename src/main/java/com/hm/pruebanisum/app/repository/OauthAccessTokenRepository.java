package com.hm.pruebanisum.app.repository;

import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface OauthAccessTokenRepository extends CrudRepository<OauthAccessToken, Long> {

    OauthAccessToken findByToken(String token);

}
