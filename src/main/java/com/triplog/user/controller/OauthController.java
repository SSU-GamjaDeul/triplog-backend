package com.triplog.user.controller;

import com.triplog.user.dto.LoginRequest;
import com.triplog.user.dto.SignupRequest;
import com.triplog.user.jwt.JwtToken;
import com.triplog.user.service.OauthService;
import com.triplog.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Oauth", description = "Oauth 관련 API입니다.")
public class OauthController {
    private final OauthService oauthService;
    private final UserService userService;

    @PostMapping("/auth/kakao")
    public ResponseEntity<JwtToken> kakaoLogin(@RequestBody LoginRequest loginRequest) {
        JwtToken jwtToken= oauthService.kakaoLogin(loginRequest.getCode());
        return ResponseEntity.ok(jwtToken);
    }


    @PostMapping("/auth/kakao/signup")
    public ResponseEntity<JwtToken> kakaoSignup(@RequestBody SignupRequest signupRequest) {
        JwtToken jwtToken = oauthService.kakaoSignup(signupRequest);
        return ResponseEntity.ok(jwtToken);
    }
}


