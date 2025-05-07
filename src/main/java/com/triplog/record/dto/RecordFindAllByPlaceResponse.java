package com.triplog.record.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordFindAllByPlaceResponse(
        List<RecordFindByPlaceResponse> records
) {
}
