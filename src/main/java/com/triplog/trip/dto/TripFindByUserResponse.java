package com.triplog.trip.dto;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripTag;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record TripFindByUserResponse (List<Item> trips) {
    @Builder
    public record Item(
            Long tripId,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            List<String> tags
    ) {
        public static Item from(Trip trip, List<TripTag> tags) {
            List<String> tagList = tags.stream()
                    .map(TripTag::getContent)
                    .toList();

            return Item.builder()
                    .tripId(trip.getId())
                    .title(trip.getTitle())
                    .startDate(trip.getStartDate())
                    .endDate(trip.getEndDate())
                    .tags(tagList)
                    .build();
        }
    }
}
