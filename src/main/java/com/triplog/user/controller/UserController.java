package com.triplog.user.controller;

import com.triplog.user.dto.LoginRequestDto;
import com.triplog.user.dto.SignupRequestDto;
import com.triplog.user.dto.UpdateProfileRequestDto;
import com.triplog.user.jwt.CustomUserDetails;
import com.triplog.user.jwt.JwtToken;
import com.triplog.user.jwt.JwtUtil;
import com.triplog.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

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

    @PatchMapping("/users")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateProfileRequestDto dto) {
        String nickname = userDetails.getUsername();
        userService.updateProfile(nickname,dto);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");

    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        userService.checkNickname(nickname);
        return ResponseEntity.ok("사용가능한 닉네임입니다.");
    }

}