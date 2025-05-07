package com.triplog.record.dto;

import com.triplog.record.domain.Record;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordFindAllByPlaceResponse(
        List<RecordFindAllByPlaceResponse.Item> records
) {
    @Builder
    public record Item(
            Long recordId,
            String title,
            String date,
            List<String> tag
    ) {
        public static Item from(Record record, List<String> tags) {
            return Item.builder()
                    .recordId(record.getId())
                    .title(record.getTitle())
                    .date(record.getDate().toString())
                    .tag(tags)
                    .build();
        }
    }
}
