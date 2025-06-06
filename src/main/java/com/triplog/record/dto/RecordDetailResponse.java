package com.triplog.record.dto;

import com.triplog.record.domain.RecordImage;
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
        List<String> tags,
        List<String> imageUrl
) {
    public static RecordDetailResponse from(Record record, List<RecordTag> tags,List<RecordImage> imageUrls) {
        List<String> tagContents = tags.stream()
                .map(RecordTag::getContent)
                .toList();

        List<String> imageUrlContents = imageUrls.stream()
                .map(RecordImage::getImageUrl)
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
                .imageUrl(imageUrlContents)
                .build();
    }
}
