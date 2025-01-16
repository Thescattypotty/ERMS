package org.employee.rms.Security.Service;

import java.util.Set;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.employee.rms.Enum.EPermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtUtilService {
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email , Collection<? extends GrantedAuthority> permissions){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withSubject(email)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
            .withClaim("permissions", permissions.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
    }

    public String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
            .build().verify(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
