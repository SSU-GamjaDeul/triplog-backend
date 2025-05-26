package com.triplog.record.repository;

import com.triplog.record.domain.RecordImage;
import com.triplog.record.domain.Record;
import com.triplog.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
    List<RecordImage> findAllByRecord(Record record);

    void deleteAllByRecord(Record record);

    @Query("SELECT ri.imageUrl FROM RecordImage ri WHERE ri.record.trip = :trip")
    List<String> findImageUrlsByTrip(Trip trip);
}
