package com.triplog.record.dto;

import com.triplog.record.domain.Record;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordFindAllByLocationResponse(
        List<RecordFindAllByLocationResponse.Item> records
) {
    @Builder
    public record Item(
            @Schema(description = "기록 id", example = "1")
            Long recordId,
            @Schema(description = "위도", example = "37.123456")
            double latitude,
            @Schema(description = "경도", example = "127.123456")
            double longitude
    ) {
        public static Item from(Record record) {
            return Item.builder()
                    .recordId(record.getId())
                    .latitude(record.getPlace().getLatitude())
                    .longitude(record.getPlace().getLongitude())
                    .build();
        }
    }
}
