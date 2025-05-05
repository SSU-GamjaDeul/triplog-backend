package com.triplog.user.controller;

import com.triplog.user.dto.LoginRequestDto;
import com.triplog.user.dto.SignupRequestDto;
import com.triplog.user.jwt.JwtToken;
import com.triplog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequestDto request) {
        String token = userService.login(request);
        return ResponseEntity.ok(JwtToken.builder().accessToken(token).build());
    }
}
