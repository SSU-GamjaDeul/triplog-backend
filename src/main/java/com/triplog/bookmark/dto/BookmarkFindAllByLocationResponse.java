package com.triplog.bookmark.dto;

import com.triplog.bookmark.domain.Bookmark;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BookmarkFindAllByLocationResponse(
        List<BookmarkFindAllByLocationResponse.Item> bookmarks
) {
    @Builder
    public record Item(
            @Schema(description = "카카오 장소 id", example = "1")
            Long kakaoPlaceId,
            @Schema(description = "위도", example = "37.123456")
            double latitude,
            @Schema(description = "경도", example = "127.123456")
            double longitude
    ) {
        public static BookmarkFindAllByLocationResponse.Item from(Bookmark bookmark) {
            return Item.builder()
                    .kakaoPlaceId(bookmark.getPlace().getKakaoPlaceId())
                    .latitude(bookmark.getPlace().getLatitude())
                    .longitude(bookmark.getPlace().getLongitude())
                    .build();
        }
    }
}
