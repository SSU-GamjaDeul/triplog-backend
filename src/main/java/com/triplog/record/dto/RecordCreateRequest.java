package com.triplog.record.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public record RecordCreateRequest (
        Long kakaoPlaceId,
        String title,
        String memo,
        LocalDate date,
        boolean isPublic,
        List<String> tags,
        List<String> imageUrl){
}
