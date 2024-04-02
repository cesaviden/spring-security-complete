package com.app.auth.util;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

    @Value("${jwt.private-key}")
    private String privateKey;

    @Value("${jwt.user-generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new java.util.Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt;

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid JWT Token");
        }
    }

    public String extractUsername(DecodedJWT jwt) {
        return jwt.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT jwt, String claimName) {
        return jwt.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT jwt) {
        return jwt.getClaims();
    }
}