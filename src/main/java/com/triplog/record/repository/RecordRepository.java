package com.triplog.record.repository;

import com.triplog.place.domain.Place;
import com.triplog.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByPlaceAndIsPublicTrueOrderByDateAsc(Place place);
}
