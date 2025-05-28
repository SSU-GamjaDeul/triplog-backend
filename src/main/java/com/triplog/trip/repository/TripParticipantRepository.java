package com.triplog.trip.repository;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
import com.triplog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripParticipantRepository extends JpaRepository<TripParticipant, Long> {
    List<TripParticipant> findByUserAndIsAcceptedTrue(User user);
    boolean existsByTripAndUser(Trip trip, User user);
    Optional<TripParticipant> findByTripAndUser(Trip trip, User user);
    List<TripParticipant> findByUserAndIsAcceptedFalse(User user);
    List<TripParticipant> findByTripAndIsAcceptedTrue(Trip trip);
    Optional<TripParticipant> findByTripAndUserAndIsAcceptedTrue(Trip trip, User user);
    List<TripParticipant> findByTrip(Trip trip);
    List<TripParticipant> findAllByUserAndIsAcceptedTrue(User user);
}
