package com.triplog.record.repository;

import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordTagRepository extends JpaRepository<RecordTag, Long> {
    List<RecordTag> findAllByRecord(Record record);

    void deleteAllByRecord(Record record);
}
