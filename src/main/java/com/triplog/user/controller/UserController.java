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
        if(userService.register(request)){
            return ResponseEntity.ok("회원가입 성공");
        }else{
            return ResponseEntity.badRequest().body("이미 존재하는 닉네임 입니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        String token = userService.login(request);
        if (token != null) {
            return ResponseEntity.ok(JwtToken.builder().accessToken(token).build());
        } else {
            return ResponseEntity.badRequest().body("로그인이 실패하였습니다.");
        }
    }
}
