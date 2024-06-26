package com.techathome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techathome.dto.LoginDTO;
import com.techathome.dto.SignInRequest;
import com.techathome.dto.SignUpRequest;
import com.techathome.services.AuthenticationService;

//@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<LoginDTO> signup(@RequestBody SignUpRequest request) {
//        return ResponseEntity.ok(authenticationService.signup(request));
//    }
//
//    @PostMapping(path = "/signin", produces = "application/json")
//    public ResponseEntity<LoginDTO> signin(@RequestBody SignInRequest request) {
//        return ResponseEntity.ok(authenticationService.signIn(request));
//    }
}
