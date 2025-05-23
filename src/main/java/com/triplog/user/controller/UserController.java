package com.triplog.user.controller;

import com.triplog.user.dto.LoginRequest;
import com.triplog.user.dto.SignupRequest;
import com.triplog.user.dto.UpdateProfileRequest;
import com.triplog.user.jwt.CustomUserDetails;
import com.triplog.user.jwt.JwtToken;
import com.triplog.user.jwt.JwtUtil;
import com.triplog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User", description = "User 관련 API입니다.")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PatchMapping("/users")
    @Operation(summary = "회원 정보 수정", description = "유저의 닉네임 또는 분위기를 수정할 수 있습니다.")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateProfileRequest dto) {
        String nickname = userDetails.getUsername();
        userService.updateProfile(nickname,dto);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    @GetMapping("/check-nickname")
    @Operation(summary = "닉네임 중복 확인", description = "입력받은 닉네임이 DB에 있는 닉네임과 중복되는지 확인합니다.")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        userService.checkNickname(nickname);
        return ResponseEntity.ok("사용가능한 닉네임입니다.");
    }

}