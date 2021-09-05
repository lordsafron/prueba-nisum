package com.hm.pruebanisum.app.models.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "oauth_access_token")
public class OauthAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long tokenId;
    @Column(length = 6000)
    private String token;
    @Column(name = "authentication_id")
    private String authenticationId;
    private LocalDateTime created;

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
    }

    public boolean validateToken(OauthAccessToken oauthAccessToken) {
        return LocalDateTime.now().plusHours(-1).isBefore(oauthAccessToken.created);
    }

}
