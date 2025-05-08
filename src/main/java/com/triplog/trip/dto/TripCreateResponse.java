package com.triplog.trip.dto;

import lombok.Builder;

@Builder
public record TripCreateResponse(Long tripId) {
}
