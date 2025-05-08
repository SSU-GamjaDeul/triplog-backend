package com.triplog.trip.dto;

import java.time.LocalDate;

public record TripCreateRequest (String title, String memo, boolean isPublic, LocalDate startDate, LocalDate endDate){ }
