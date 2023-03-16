package com.hm.pruebanisum.app.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.exceptions.DataBaseException;
import com.hm.pruebanisum.app.models.entities.OauthAccessToken;
import com.hm.pruebanisum.app.repository.OauthAccessTokenRepository;
import com.hm.pruebanisum.app.services.IOauthAccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthAccessTokenServiceImpl implements IOauthAccessTokenService {

    private final OauthAccessTokenRepository repository;

    private final ObjectMapper mapper;

    @Override
    @Transactional
    public OauthAccessToken save(OauthAccessToken oauthAccessToken) throws JsonProcessingException {
        try {
            return repository.save(oauthAccessToken);
        } catch (Exception ex) {
            log.error("OauthAccessTokenServiceImpl::save --oauthAccessToken [{}] --exCause [{}] --exMessage [{}]", mapper.writeValueAsString(oauthAccessToken), ex.getCause(), ex.getMessage());
            throw new DataBaseException("problemas con el banco de datos");
        }
    }
}
