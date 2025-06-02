package com.triplog.trip.dto;

import java.util.List;

public record TripUpdateRequest(String title,
                                String memo,
                                boolean isPublic,
                                List<String> tags) {
}
