package com.rendezvous.demo.Configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;
@Configuration
public class KeycloakAdminConfig {
    @Bean
    public Keycloak  keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("Rendez-vous")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("backend-cllient")
                .clientSecret("dWd0Ik5JRpAtKvU4V4Jt3VpQZeswZwEf")
                .build();
    }
}
