package com.triplog.trip.dto;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripTag;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record TripDetailResponse(
        Long tripId,
        String title,
        String memo,
        boolean isPublic,
        LocalDate startDate,
        LocalDate endDate,
        List<String> tags) {
    public static TripDetailResponse from(Trip trip, List <TripTag> tags){
        List<String> tagList = tags.stream()
                .map(TripTag::getContent)
                .toList();
        return TripDetailResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .memo(trip.getMemo())
                .isPublic(trip.isPublic())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .tags(tagList)
                .build();
    }
}
