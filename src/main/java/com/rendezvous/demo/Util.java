package com.rendezvous.demo;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class Util {
    public static UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
