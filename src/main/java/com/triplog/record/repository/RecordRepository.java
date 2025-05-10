package com.triplog.record.repository;

import com.triplog.place.domain.Place;
import com.triplog.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByPlaceAndIsPublicTrueOrderByDateDesc(Place place);
    Optional<Record> findById(Long id);
}
