package com.triplog.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProfileRequestDto {
    private String nickname;
    private List<String> vibe;
}
