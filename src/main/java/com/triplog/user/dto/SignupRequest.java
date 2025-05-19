package com.triplog.user.dto;

import com.triplog.user.domain.enums.Gender;
import lombok.Getter;

import java.util.List;

public record SignupRequest(
        String nickname,
        String birthYear,
        Gender gender,
        List<String> Vibe
){}