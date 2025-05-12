package com.triplog.trip.repository;

import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripTagRepository extends JpaRepository<TripTag, Long> {
    List<TripTag> findByTrip(Trip trip);
}