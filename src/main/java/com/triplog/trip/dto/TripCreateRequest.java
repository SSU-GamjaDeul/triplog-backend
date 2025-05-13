package com.triplog.trip.dto;

import java.time.LocalDate;
import java.util.List;

public record TripCreateRequest (String title, String memo, boolean isPublic, LocalDate startDate, LocalDate endDate, List<String> tags){ }
