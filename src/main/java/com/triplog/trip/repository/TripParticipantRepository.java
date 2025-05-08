package com.triplog.trip.repository;

import com.triplog.trip.domain.TripParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripParticipantRepository extends JpaRepository<TripParticipant, Long> {
}
