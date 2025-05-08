package com.triplog.record.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecordUpdateDto {
    private String title;
    private String memo;
    private LocalDate date;
    private boolean is_public;
}
