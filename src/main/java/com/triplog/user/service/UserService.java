package com.triplog.user.service;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.user.domain.User;
import com.triplog.user.domain.UserVibe;
import com.triplog.user.domain.enums.Vibe;
import com.triplog.user.dto.LoginRequestDto;
import com.triplog.user.dto.SignupRequestDto;
import com.triplog.user.jwt.JwtUtil;
import com.triplog.user.repository.UserRepository;
import com.triplog.user.repository.UserVibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserVibeRepository userVibeRepository;
    private final JwtUtil jwtUtil;

    // 회원가입
    public boolean register(SignupRequestDto signupRequestDto) {
        //이미 등록된 닉네임인 경우 에러처리
        if(userRepository.existsByNickname(signupRequestDto.getNickname())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        //User 저장
        User user=userRepository.save(User.builder()
                .nickname(signupRequestDto.getNickname())
                .build());

        // 선택된 모든 Vibe 저장
        for (String vibeDesc : signupRequestDto.getVibe()) {
            Vibe vibeEnum = Vibe.fromDescription(vibeDesc); // 한글 → enum 매핑

            UserVibe userVibe = UserVibe.builder()
                    .user(user)
                    .vibe(vibeEnum)
                    .build();

            userVibeRepository.save(userVibe);
        }

        return true;

    }

    public String login(LoginRequestDto loginRequestDto) {
        //등록되지 않은 user인 경우 에러 처리
        if(!userRepository.existsByNickname(loginRequestDto.getNickname())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        String accessToken=jwtUtil.createAccessToken(loginRequestDto);
        return accessToken;
    }

}
