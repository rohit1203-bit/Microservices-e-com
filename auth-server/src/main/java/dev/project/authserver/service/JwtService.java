package dev.project.authserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret-key:SECRET_KEY}")
    private String SECRET_KEY;

    public String createToken(String username, Long id) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1hr

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(username)
                .withIssuer(id+":"+username)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public String decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject();
    }

    public boolean isExpired(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);
        Date now = new Date();
        return decoded.getExpiresAt().getTime() < now.getTime();
    }

}
