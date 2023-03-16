package com.hm.pruebanisum.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.pruebanisum.app.util.JWTTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Order(1)
public class AuthFilter implements Filter {

    @Autowired
    private JWTTokenManager jwtTokenManager;

    @Autowired
    private ObjectMapper mapper;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Objects.nonNull(((HttpServletRequest) request).getRequestURI()) &&
                (((HttpServletRequest) request).getRequestURI().contains("/user") ||
                ((HttpServletRequest) request).getRequestURI().contains("/swagger") ||
                ((HttpServletRequest) request).getRequestURI().contains("/api-docs")) &&
                (((HttpServletRequest) request).getMethod().equals("POST") ||
                ((HttpServletRequest) request).getMethod().equals("GET"))) {
            log.info("metodo: " + ((HttpServletRequest) request).getMethod());
            chain.doFilter(request, response);
            return;
        }
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        if (Objects.isNull(token)) {
            response(response, "Recurso no autorizado", HttpStatus.UNAUTHORIZED);
            return;
        }

        try {
            jwtTokenManager.decodeJWT(token);
        } catch (Exception ex) {
            response(response, ex.getMessage(), HttpStatus.FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);

    }

    private void response(ServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        var httpServletResponse = (HttpServletResponse) response;

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("mensaje", message);

        httpServletResponse.setStatus(httpStatus.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(httpServletResponse.getWriter(), errorDetails);
    }

}
