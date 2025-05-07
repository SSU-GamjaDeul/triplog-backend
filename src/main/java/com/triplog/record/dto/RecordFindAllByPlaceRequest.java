package com.triplog.record.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecordFindAllByPlaceRequest(
        @Schema(description = "카카오 장소 id", nullable = false, example = "1")
        Long kakaoPlaceId
) {
}
