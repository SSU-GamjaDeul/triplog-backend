package com.triplog.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FileUploadResponse(
        @Schema(description = "FILE URL", nullable = false, example = "...")
        String fileUrl
) {
}
