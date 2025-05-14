package com.triplog.record.dto;

import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordImage;
import com.triplog.record.domain.RecordTag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record RecordFindAllByTripResponse (
        List<RecordFindAllByTripResponse.Item> records
) {
    @Builder
    public record Item(
            @Schema(description = "기록 id", nullable = false, example = "1")
            Long recordId,
            @Schema(description = "제목", nullable = false, example = "...")
            String title,
            @Schema(description = "날짜", nullable = false, example = "2025.06.20")
            String date,
            @ArraySchema(
                    arraySchema = @Schema(description = "태그 목록", nullable = false),
                    schema = @Schema(example = "감성")
            )
            List<String> tag,
            List<String> imageUrls
    ) {
        public static Item from(Record record, List<RecordTag> tags, List<RecordImage> imageUrls) {
            List<String> tagContents = tags.stream()
                    .map(RecordTag::getContent)
                    .toList();
            List<String> imageUrlContents = imageUrls.stream()
                    .map(RecordImage::getImageUrl)
                    .toList();

            String formattedDate = record.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

            return Item.builder()
                    .recordId(record.getId())
                    .title(record.getTitle())
                    .date(formattedDate)
                    .tag(tagContents)
                    .imageUrls(imageUrlContents)
                    .build();
        }
    }
}
