package com.triplog.trip;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.trip.domain.*;
import com.triplog.trip.dto.*;
import com.triplog.trip.repository.*;
import com.triplog.user.domain.User;
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
                .inviter(user.getNickname())
                .isAccepted(true)
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

    public TripDetailResponse getTripDetail(Long tripId) {
        Trip trip = tripFinder.findByTripId(tripId);
        List<TripTag> tags = tripTagRepository.findByTrip(trip);
        List<TripParticipant> participants = tripParticipantRepository.findByTripAndIsAcceptedTrue(trip);
        return TripDetailResponse.from(trip, tags, participants);
    }

    @Transactional
    public void inviteTrip(String username, Long tripId, TripInviteRequest request) {
        User user = userFinder.findByNickname(username);
        Trip trip = tripFinder.findByTripId(tripId);
        User invitedUser = userFinder.findByNickname(request.nickname());

        // 현재 로그인한 유저가 여행 참여자인지 확인
        tripParticipantRepository.findByTripAndUserAndIsAcceptedTrue(trip, user)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_ACCESS));

        // 자기 자신 초대 방지
        if (user.equals(invitedUser)){
           throw new CustomException(ErrorCode.CANNOT_INVITE_SELF);
        }

        // 이미 초대된 유저인지 확인
        boolean alreadyInvited = tripParticipantRepository
                .existsByTripAndUser(trip, invitedUser);
        if (alreadyInvited) {
            throw new CustomException(ErrorCode.ALREADY_INVITED);
        }

        TripParticipant invitedParticipant = TripParticipant.builder()
                .user(invitedUser)
                .trip(trip)
                .inviter(user.getNickname())
                .isAccepted(false)
                .build();

        tripParticipantRepository.save(invitedParticipant);
    }

    public TripInviteFindByUserResponse getTripInvitesByUser(String username) {
        User user = userFinder.findByNickname(username);

        List<TripParticipant> pendingList = tripParticipantRepository.findByUserAndIsAcceptedFalse(user);

        List<TripInviteFindByUserResponse.Item> responseItems = pendingList.stream()
                .map(TripInviteFindByUserResponse.Item::from)
                .toList();

        return TripInviteFindByUserResponse.builder()
                .tripInvites(responseItems)
                .build();
    }

    @Transactional
    public void acceptInvite(String username, Long tripId) {
        User user = userFinder.findByNickname(username);
        Trip trip = tripFinder.findByTripId(tripId);

        TripParticipant tripParticipant = tripParticipantRepository
                .findByTripAndUser(trip, user)
                        .orElseThrow(() -> new CustomException(ErrorCode.INVITE_NOT_FOUND));

        if (tripParticipant.isAccepted()) {
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED);
        }

        tripParticipant.accept();
    }

    @Transactional
    public void refuseInvite(String username, Long tripId) {
        User user = userFinder.findByNickname(username);
        Trip trip = tripFinder.findByTripId(tripId);

        TripParticipant tripParticipant = tripParticipantRepository
                .findByTripAndUser(trip, user)
                .orElseThrow(() -> new CustomException(ErrorCode.INVITE_NOT_FOUND));

        if (tripParticipant.isAccepted()) {
            throw new CustomException(ErrorCode.ALREADY_ACCEPTED);
        }

        tripParticipantRepository.delete(tripParticipant);
    }

    @Transactional
    public void updateTrip(String username, Long tripId, TripUpdateRequest request) {
        User user = userFinder.findByNickname(username);
        Trip trip = tripFinder.findByTripId(tripId);

        tripParticipantRepository.findByTripAndUserAndIsAcceptedTrue(trip, user)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_ACCESS));

        trip.update(request.title(),
                request.memo(),
                request.isPublic(),
                request.startDate(),
                request.endDate());

        tripTagRepository.deleteAllByTrip(trip);
        List<String> newTags = request.tags();
        if (newTags != null) {
            for (String newContent : newTags) {
                TripTag tripTag = TripTag.builder()
                        .trip(trip)
                        .content(newContent)
                        .build();
                tripTagRepository.save(tripTag);
            }
        }
    }
}