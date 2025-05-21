package com.triplog.trip.dto;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
import lombok.Builder;

import java.util.List;

@Builder
public record TripInviteFindByUserResponse(List<Item> tripInvites) {
    @Builder
    public record Item(
            Long tripId,
            String title,
            String inviter
    ) {
        public static Item from(TripParticipant tripParticipant) {
            Trip trip = tripParticipant.getTrip();
            String inviter = tripParticipant.getInviter();

            return Item.builder()
                    .tripId(trip.getId())
                    .title(trip.getTitle())
                    .inviter(inviter)
                    .build();
        }
    }
}
