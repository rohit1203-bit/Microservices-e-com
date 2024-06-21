package dev.project.apigateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret-key:SECRET_KEY}")
    private String SECRET_KEY;

    public String decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        return decoded.getIssuer();
    }

    public boolean isExpired(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        Date now = new Date();
        return decoded.getExpiresAt().getTime() < now.getTime();
    }
}
