package com.triplog.trip.repository;

import com.triplog.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long tripId);
}