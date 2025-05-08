package com.triplog.record.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecordCreateDto {
    private Long kakaoPlaceId;
    private String title;
    private String memo;
    private LocalDate date;
    private boolean is_public;
}
