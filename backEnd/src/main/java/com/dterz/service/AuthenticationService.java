package com.dterz.service;

import java.util.Objects;

import javax.transaction.Transactional;

import com.dterz.model.JwtResponse;
import com.dterz.model.UserPrincipal;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * Authenticates the User from the credentials provided during log in
     *
     * @param username the username provided
     * @param password the plain text password provided
     * @return JwtResponse
     */
    public JwtResponse authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserPrincipal userDetails = userService.loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);
        return JwtResponse.builder().jwttoken(token).userId(userDetails.getUserId()).isAdmin(userDetails.isSuperAdmin()).build();
    }

    public JwtResponse logout(String username) {
        return null;
    }
}
