package com.example.issue_management_system.security.jwt;

import com.example.issue_management_system.security.service.CustomUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JwtUtils {

    private final long JWT_EXPIRATION = 604800000L;
    private final String JWT_SECRET = "8HkN8UfSnWa/151AHr2axaRkwnGC4+hF9G/wIc+7OKRSyp7qR8jHbdsJiNg6oPisPZc+J4fsaB6ZfMG2NWc4SA==";

    public String generateJWT(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<String> roles = Objects.requireNonNull(userDetails).getAuthorities().stream().map(
                GrantedAuthority::getAuthority
        ).toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("id", userDetails.getId())
                .claim("username", userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + JWT_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public boolean validateJwt(String jwt) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

}
