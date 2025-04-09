package org.example.tmstrainingspring.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.tmstrainingspring.dtos.UserDTO;

import java.util.Date;

public class JwtUtil {
    private static final String key = "secret";
    private static final Algorithm algorithm = Algorithm.HMAC256(key);

    public static String generateJWT(UserDTO user) {
        return JWT
                .create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1000 * 60 * 5))
                .sign(algorithm);
    }

    public static DecodedJWT verifyJWT(String token) {
        return JWT.require(algorithm).build().verify(token);
    }
}
