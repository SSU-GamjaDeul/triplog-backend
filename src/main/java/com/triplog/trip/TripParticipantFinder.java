package com.triplog.trip;

import com.triplog.trip.domain.TripParticipant;
import com.triplog.trip.repository.TripParticipantRepository;
import com.triplog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TripParticipantFinder {
    private final TripParticipantRepository tripParticipantRepository;

    public List<TripParticipant> findAllByUserAndIsAcceptedTrue(User user) {
        return tripParticipantRepository.findAllByUserAndIsAcceptedTrue(user);
    }
}
