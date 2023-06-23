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

/*
    회원가입, 로그인 api
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    /*
        로그인
        1. 클라이언트로부터 아이디 비밀번호를 받아 아이디, 비밀번호 검증
        2. 아이디, 비밀번호 일치하면 jwt token 발행
        3. 토큰 리스폰스
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid Auth.SignIn request) {
        User user = authService.authenticate(request);
        String token = tokenProvider.generateToken(user.getUsername(), user.getRoles());
        log.info("user login -> " + user.getUsername());
        return ResponseEntity.ok(token);
    }

    /*
        회원가입
        1. 회원가입에 필요한 정보들(SignUp Dto) 받아서 회원가입 진행
        2. 회원가입에 성공하면 userEntity 객체를 SignUpResponse Dto 객체로 변환해서 리턴
     */
    @PostMapping("/signup")
    public ResponseEntity<Auth.SignUpResponse> singUp(@RequestBody @Valid Auth.SignUp request){
        User user = authService.register(request);
        log.info("user signup -> " + user.getUsername());
        return ResponseEntity.ok(Auth.SignUpResponse.fromEntity(user));
    }

}
