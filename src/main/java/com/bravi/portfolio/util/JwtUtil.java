package com.bravi.portfolio.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtil {

    public static String generateToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String jwt = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(asMinutes(15))
                .sign(algorithm);

        return jwt;
    }

    public static String decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject();
    }

    private static Date asMinutes(Integer minutes) {
        return new Date(System.currentTimeMillis() * 1000 * 60 * minutes);
    }


}
