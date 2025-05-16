package com.triplog.trip;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.record.domain.RecordTag;
import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
import com.triplog.trip.domain.TripTag;
import com.triplog.trip.dto.TripDetailResponse;
import com.triplog.trip.dto.TripFindByUserResponse;
import com.triplog.trip.repository.TripTagRepository;
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
    private final TripTagRepository tripTagRepository;
    private final UserFinder userFinder;
    private final TripFinder tripFinder;


    @Transactional
    public TripCreateResponse createTrip(TripCreateRequest request, String username) {
        User user = userFinder.findByNickname(username);

        Trip trip = Trip.builder()
                .title(request.title())
                .memo(request.memo())
                .isPublic(request.isPublic())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .user(user)
                .build();

        Trip savedTrip = tripRepository.save(trip);

        TripParticipant participant = TripParticipant.builder()
                .user(user)
                .trip(savedTrip)
                .build();

        tripParticipantRepository.save(participant);

        List<String> tripTagContents = request.tags();
        for(String tripTagContent:tripTagContents){
            TripTag tripTag = TripTag.builder()
                    .trip(savedTrip)
                    .content(tripTagContent)
                    .build();
            tripTagRepository.save(tripTag);
        }

        return TripCreateResponse.builder()
                .tripId(savedTrip.getId())
                .build();
    }

    public TripFindByUserResponse getTripsByUser(String username) {
        User user = userFinder.findByNickname(username);

        List<TripParticipant> participationList = tripParticipantRepository.findByUser(user);

        List<TripFindByUserResponse.Item> responseItems = participationList.stream()
                .map(participant -> {
                    Trip trip = participant.getTrip();
                    List<TripTag> tags = tripTagRepository.findByTrip(trip);
                    return TripFindByUserResponse.Item.from(trip, tags);
                })
                .toList();

        return TripFindByUserResponse.builder()
                .trips(responseItems)
                .build();
    }

    public TripDetailResponse getTripDetail(Long trip_id) {
        Trip trip = tripFinder.findByTripId(trip_id);
        List<TripTag> tags = tripTagRepository.findByTrip(trip);
        return TripDetailResponse.from(trip, tags);
    }

}