package com.triplog.user.service;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.user.UserFinder;
import com.triplog.user.domain.User;
import com.triplog.user.domain.UserVibe;
import com.triplog.user.domain.enums.Vibe;
import com.triplog.user.dto.LoginRequest;
import com.triplog.user.dto.ProfileResponse;
import com.triplog.user.dto.SignupRequest;
import com.triplog.user.dto.UpdateProfileRequest;
import com.triplog.user.jwt.JwtUtil;
import com.triplog.user.repository.UserRepository;
import com.triplog.user.repository.UserVibeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserVibeRepository userVibeRepository;
    private final JwtUtil jwtUtil;
    private final UserFinder userFinder;

    @Transactional
    public void updateProfile(String nickname, UpdateProfileRequest updateProfileRequestDto) {
        User user=userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(updateProfileRequestDto.nickname()!=null && !updateProfileRequestDto.nickname().equals(nickname)) {
            if(userRepository.existsByNickname(updateProfileRequestDto.nickname())) {
                throw new CustomException(ErrorCode.DUPLICATE_USER);
            }
            user.updateProfile(updateProfileRequestDto.nickname(), updateProfileRequestDto.birthYear(), updateProfileRequestDto.gender());
        }

        if(updateProfileRequestDto.vibe()!=null && !updateProfileRequestDto.vibe().isEmpty()) {
            userVibeRepository.deleteAllByUser(user);

            for(String vibeDesc : updateProfileRequestDto.vibe()) {
                Vibe vibeEnum = Vibe.fromDescription(vibeDesc);
                UserVibe userVibe=UserVibe.builder()
                        .user(user)
                        .vibe(vibeEnum)
                        .build();
                userVibeRepository.save(userVibe);
            }
        }
    }

    public void checkNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()) throw new CustomException(ErrorCode.DUPLICATE_USER);
    }

    public ProfileResponse getProfile(String nickname) {
        User user=userFinder.findByNickname(nickname);
        ProfileResponse profileResponse=ProfileResponse.builder()
                .nickname(nickname)
                .email(user.getEmail())
                .birthYear(user.getBirthYear())
                .gender(user.getGender().toString())
                .build();
        return profileResponse;
    }
}
