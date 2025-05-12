package com.triplog.trip;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
import com.triplog.user.domain.User;
import com.triplog.trip.dto.TripCreateRequest;
import com.triplog.trip.dto.TripCreateResponse;
import com.triplog.trip.repository.TripParticipantRepository;
import com.triplog.trip.repository.TripRepository;
import com.triplog.user.UserFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final TripParticipantRepository tripParticipantRepository;
    private final UserFinder userFinder;


    @Transactional
    public TripCreateResponse createTrip(TripCreateRequest request, String username) {
        User user = userFinder.findByNickname(username);

        Trip trip = Trip.builder()
                .title(request.title())
                .memo(request.memo())
                .isPublic(request.isPublic())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();

        Trip savedTrip = tripRepository.save(trip);

        TripParticipant participant = TripParticipant.builder()
                .user(user)
                .trip(savedTrip)
                .build();

        tripParticipantRepository.save(participant);

        return TripCreateResponse.builder()
                .tripId(savedTrip.getId())
                .build();
    }

}