package com.esliceu.forum.forum.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;

    @Value("${tokenExpirationTime}")
    int tokenExpirationTime;

    @Autowired
    UserRepo userRepo;

    public String generateToken(Long userid) {
        Optional<User> optionalUser = userRepo.findById(userid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return JWT.create()
                    .withSubject(String.valueOf(user.getUser_id()))
                    .withClaim("role", user.getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                    .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        }
        return "";
    }

    public Map<String, Claim> getClaims(String token) {
        Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getClaims();

        return claims;
    }
}
