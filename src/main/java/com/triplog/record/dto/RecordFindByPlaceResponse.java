package com.triplog.record.dto;

import com.triplog.record.domain.Record;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordFindByPlaceResponse(
        Long recordId,
        String title,
        String date,
        List<String> tag
) {
    public static RecordFindByPlaceResponse from(Record record, List<String> tags) {
        return RecordFindByPlaceResponse.builder()
                .recordId(record.getId())
                .title(record.getTitle())
                .date(record.getDate().toString())
                .tag(tags)
                .build();
    }
}
