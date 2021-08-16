package com.ot6.mercadolivre.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${mercadolivre.jwt.expiration}")
    private Long tokenExpirationTimeInMillis;

    @Value("${mercadolivre.jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        UserDetails user = (UserDetails) auth.getPrincipal();

        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + tokenExpirationTimeInMillis);

        return Jwts
                .builder()
                .setIssuer("Desafio API Mercado Livre - OT")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String getUserEmailFrom(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
