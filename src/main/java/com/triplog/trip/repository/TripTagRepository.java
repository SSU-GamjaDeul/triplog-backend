package com.triplog.trip.repository;

import com.triplog.trip.domain.TripTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripTagRepository extends JpaRepository<TripTag, Long> {
}
