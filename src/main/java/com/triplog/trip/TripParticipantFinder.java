package com.triplog.trip;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
import com.triplog.trip.repository.TripParticipantRepository;
import com.triplog.trip.repository.TripRepository;
import com.triplog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TripParticipantFinder {
    private final TripParticipantRepository tripParticipantRepository;

    public List<TripParticipant> findAllByUser(User user) {
        return tripParticipantRepository.findByUser(user);
    }
}
