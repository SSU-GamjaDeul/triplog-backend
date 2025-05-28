package com.triplog.bookmark.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BookmarkCheckResponse(
        @Schema(description = "북마크 유무", nullable = false, example = "true")
        boolean isBookmarked
) {}
