package com.devhong.reservation.controller;

import com.devhong.reservation.dto.Auth;
import com.devhong.reservation.model.User;
import com.devhong.reservation.security.TokenProvider;
import com.devhong.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid Auth.SignIn request) {
        User user = authService.authenticate(request);
        String token = tokenProvider.generateToken(user.getUsername(), user.getRoles());
        log.info("user login -> " + user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Auth.SignUpResponse> singUp(@RequestBody @Valid Auth.SignUp request){
        User user = authService.register(request);
        log.info("user signup -> " + user.getUsername());
        return ResponseEntity.ok(Auth.SignUpResponse.fromEntity(user));
    }

}
