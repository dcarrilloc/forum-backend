package com.esliceu.forum.forum.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.esliceu.forum.forum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;
        String authorizationHeader = request.getHeader("Authorization");

        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Map<String, Claim> userDetails = tokenService.getClaims(token);
            request.setAttribute("userDetailsFromToken", userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
