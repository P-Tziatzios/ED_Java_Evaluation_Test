package com.dterz.controllers;

import com.dterz.model.JwtRequest;
import com.dterz.model.JwtResponse;
import com.dterz.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticates the User from the credentials provided during log in
     *
     * @param authenticationRequest provided credentials
     * @return ResponseEntity<?>
     * @throws Exception throws an Exception
     */
    @PostMapping("/api/authenticate")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        JwtResponse response = authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(@RequestBody String username) {
        JwtResponse response = authenticationService.logout(username);
        return ResponseEntity.ok(response);
    }
}
