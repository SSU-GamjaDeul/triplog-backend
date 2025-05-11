package com.triplog.record.dto;

import java.time.LocalDate;
import java.util.List;

public record RecordUpdateRequest(
        String title,
        String memo,
        LocalDate date,
        boolean isPublic,
        List<String> tags
){}
