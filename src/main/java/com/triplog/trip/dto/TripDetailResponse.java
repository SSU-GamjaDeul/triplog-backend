package com.triplog.trip.dto;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
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
        List<String> tags,
        List<String> participants ) {
    public static TripDetailResponse from(Trip trip, List <TripTag> tags, List<TripParticipant> participants ){
        List<String> tagList = tags.stream()
                .map(TripTag::getContent)
                .toList();

        List<String> participantList = participants.stream()
                .map(p -> p.getUser().getNickname())
                .toList();

        return TripDetailResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .memo(trip.getMemo())
                .isPublic(trip.isPublic())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .tags(tagList)
                .participants(participantList)
                .build();
    }
}
