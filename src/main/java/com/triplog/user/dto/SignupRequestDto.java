package com.triplog.user.dto;

import com.triplog.user.domain.enums.Vibe;
import lombok.Getter;

import java.util.List;

@Getter
public class SignupRequestDto {
    private String nickname;
    private List<String> vibe;
}
