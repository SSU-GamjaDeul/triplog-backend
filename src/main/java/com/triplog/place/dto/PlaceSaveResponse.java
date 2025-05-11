package com.triplog.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlaceSaveResponse(
        @Schema(description = "장소 ID", example = "1")
        Long placeId
) {}
