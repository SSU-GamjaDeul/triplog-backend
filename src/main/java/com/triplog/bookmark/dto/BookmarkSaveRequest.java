package com.triplog.bookmark.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookmarkSaveRequest(
        @Schema(description = "카카오 장소 id", nullable = false, example = "1")
        Long kakaoPlaceId
) {}
