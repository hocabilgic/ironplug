package com.ironplug.security.jwt;


import com.ironplug.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtUtils {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateJwtTokenFromEmail(userDetails.getEmail());
    }

    public String generateJwtTokenFromEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Jwt token is expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Jwt token is unsupported : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Jwt token is invalid : {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("Jwt token is invalid : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Jwt  is empty : {}", e.getMessage());
        }
        return false;
    }

    public String getEmailFromJwtToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
