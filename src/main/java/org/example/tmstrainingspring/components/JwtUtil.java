package org.example.tmstrainingspring.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.tmstrainingspring.dtos.UserDTO;
import org.example.tmstrainingspring.exceptions.UnauthorizedException;

import java.util.Date;

public class JwtUtil {
    private static final String key = "secret";
    private static final Algorithm algorithm = Algorithm.HMAC256(key);

    public static String generateJWT(UserDTO user) {
        return JWT
                .create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("age", user.getAge())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1000 * 60 * 5))
                .sign(algorithm);
    }

    public static UserDTO verifyJWT(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
            return new UserDTO(
                    decodedJWT.getClaim("id").asInt(),
                    decodedJWT.getClaim("username").asString(),
                    decodedJWT.getClaim("firstName").asString(),
                    decodedJWT.getClaim("lastName").asString(),
                    decodedJWT.getClaim("age").asInt()
            );

        } catch (JWTVerificationException e) {
            throw new UnauthorizedException("Invalid JWT");
        }
    }


}
