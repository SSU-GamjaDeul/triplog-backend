package com.triplog.record.dto;

import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordTag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record RecordFindAllByUserResponse(
        List<RecordFindAllByUserResponse.Item> records
){
    @Builder
    public record Item(
            @Schema(description = "제목", nullable = false, example = "...")
            String title,
            @Schema(description = "메모", nullable = false, example = "...")
            String memo,
            @Schema(description = "날짜", nullable = false, example = "2025.06.20")
            String date,
            @Schema(description = "주소", nullable = false, example = "...")
            String address,
            @ArraySchema(
                    arraySchema = @Schema(description = "태그 목록", nullable = false),
                    schema = @Schema(example = "감성")
            )
            List<String> tag
    ){
        public static RecordFindAllByUserResponse.Item from(Record record, List<RecordTag> tags) {
            List<String> tagContents = tags.stream()
                    .map(RecordTag::getContent)
                    .toList();

            String formattedDate = record.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

            return Item.builder()
                    .title(record.getTitle())
                    .memo(record.getMemo())
                    .date(formattedDate)
                    .tag(tagContents)
                    .address(record.getPlace().getAddress())
                    .build();
        }
    }
}
