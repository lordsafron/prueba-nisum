package com.hm.pruebanisum.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name = "oauth_access_token")
public class OauthAccessToken extends AuditEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long tokenId;
    @Column(length = 6000)
    private String token;
    @Column(name = "authentication_id")
    private String authenticationId;

    public boolean validateToken(OauthAccessToken oauthAccessToken) {
        return LocalDateTime.now().plusHours(-1).isBefore(super.getCreatedAt());
    }

}
