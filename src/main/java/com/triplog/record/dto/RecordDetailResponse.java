package com.triplog.record.dto;

import com.triplog.record.domain.RecordTag;
import lombok.Builder;
import com.triplog.record.domain.Record;
import java.util.List;

@Builder
public record RecordDetailResponse (
        Long recordId,
        String title,
        String memo,
        String date,
        boolean isPublic,
        String tripTitle,
        String placeName,
        double latitude,
        double longitude,
        List<String> tags
) {
    public static RecordDetailResponse from(Record record, List<RecordTag> tags) {
        List<String> tagContents = tags.stream()
                .map(RecordTag::getContent)
                .toList();
        return RecordDetailResponse.builder()
                .recordId(record.getId())
                .title(record.getTitle())
                .memo(record.getMemo())
                .date(record.getDate().toString()) // 포맷 조정 가능
                .isPublic(record.isPublic())
                .tripTitle(record.getTrip().getTitle())
                .placeName(record.getPlace().getName())
                .latitude(record.getPlace().getLatitude())
                .longitude(record.getPlace().getLongitude())
                .tags(tagContents)
                .build();
    }
}
