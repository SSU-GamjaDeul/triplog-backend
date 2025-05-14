package com.triplog.record.repository;

import com.triplog.record.domain.RecordImage;
import com.triplog.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
    List<RecordImage> findAllByRecord(Record record);

    void deleteAllByRecord(Record record);
}
