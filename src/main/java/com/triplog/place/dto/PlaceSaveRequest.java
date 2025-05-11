package com.triplog.place.dto;

import com.triplog.place.domain.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceSaveRequest(
        @Schema(description = "장소 이름", example = "스타벅스 강남점")
        String name,

        @Schema(description = "위도", example = "37.4979")
        double latitude,

        @Schema(description = "경도", example = "127.0276")
        double longitude,

        @Schema(description = "주소", example = "서울특별시 강남구 강남대로 364")
        String address,

        @Schema(description = "장소 카테고리", example = "CAFE")
        Category category,

        @Schema(description = "카카오 장소 ID", example = "123456789")
        Long kakaoPlaceId
) {}
