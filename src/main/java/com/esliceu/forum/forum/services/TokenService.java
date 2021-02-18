package com.esliceu.forum.forum.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;

    @Value("${tokenExpirationTime}")
    int tokenExpirationTime;

    @Autowired
    UserRepo userRepo;

    public String generateToken(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getUser_id())
                    .withClaim("name", user.getName())
                    .withClaim("role", user.getRole())
                    .withClaim("avatar", user.getAvatar())
                    .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                    .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        }
        return "";
    }

    public Map<String, Claim> getClaims(String token) {
        Map userDetails = new HashMap();
        String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getClaims();

        /*
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().asString());
        }
         */

        return claims;
    }
}
