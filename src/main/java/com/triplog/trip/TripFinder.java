package com.triplog.trip;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.trip.domain.Trip;
import com.triplog.trip.repository.TripRepository;
import com.triplog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripFinder {
    private final TripRepository tripRepository;

    public Trip findByTripId(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.TRIP_NOT_FOUND));
    }
}
