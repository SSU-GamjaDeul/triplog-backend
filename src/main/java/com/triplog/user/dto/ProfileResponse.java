package com.triplog.user.dto;

import lombok.Builder;

@Builder
public record ProfileResponse(
        String nickname,
        String email,
        String birthYear,
        String gender
) {
}
