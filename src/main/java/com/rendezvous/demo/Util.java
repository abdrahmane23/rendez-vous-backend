package com.rendezvous.demo;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.keycloak.OAuth2Constants.JWT;

public class Util {
    public static UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
    public static String extractRole(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess == null || !realmAccess.containsKey("roles")) {
            return null;
        }

        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
                .filter(role -> role.equals("EXAMINER") || role.equals("PATIENT") || role.equals("ADMIN"))
                .findFirst()
                .orElse(null);
    }

}
