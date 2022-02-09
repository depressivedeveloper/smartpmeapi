package com.pmesmart.smartapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmesmart.smartapi.data.UserDetailsData;
import com.pmesmart.smartapi.model.User;

import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authManager;
    public static final int TOKEN_EXPIRES = 600_000;
    public static final String TOKEN_PASSWORD = "59526410-04f2-41a8-9e6c-e2a859a48865";

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public org.springframework.security.core.Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws org.springframework.security.core.AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), 
                user.getPassword(),
                new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Mensagem de erro ao autenticar: "+e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            org.springframework.security.core.Authentication authResult) throws IOException, ServletException {
        
        UserDetailsData userDetailsData = (UserDetailsData) authResult.getPrincipal();

        String token  = JWT.create()
        .withSubject(userDetailsData.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRES))
        .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();
    }

}