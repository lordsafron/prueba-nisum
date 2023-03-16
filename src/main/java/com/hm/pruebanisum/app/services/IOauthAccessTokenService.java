package com.hm.pruebanisum.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hm.pruebanisum.app.models.entities.OauthAccessToken;

public interface IOauthAccessTokenService {
    OauthAccessToken save(OauthAccessToken oauthAccessToken) throws JsonProcessingException;
}
