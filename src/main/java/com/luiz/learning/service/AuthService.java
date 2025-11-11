package com.luiz.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luiz.learning.config.JwtService;
import com.luiz.learning.dto.LoginDTO;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String login(LoginDTO loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.usuario(),
                        loginRequest.senha())
        );

        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(ga -> ga.getAuthority().equals("ADMIN"));

        if(!isAdmin) {
            throw new UnsupportedOperationException();
        }

        return jwtService.createToken(auth);
    }
}