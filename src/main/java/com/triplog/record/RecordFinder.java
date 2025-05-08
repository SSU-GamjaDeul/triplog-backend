package com.triplog.record;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.record.domain.Record;
import com.triplog.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordFinder {
    private final RecordRepository recordRepository;

    public Record findByRecordId(Long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(()-> new CustomException(ErrorCode.RECORD_NOT_FOUND));
    }
}
