package com.triplog.record.service;

import com.triplog.place.PlaceFinder;
import com.triplog.place.domain.Place;
import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordTag;
import com.triplog.record.dto.RecordFindAllByPlaceResponse;
import com.triplog.record.dto.RecordFindByPlaceResponse;
import com.triplog.record.repository.RecordRepository;
import com.triplog.record.repository.RecordTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordTagRepository recordTagRepository;
    private final PlaceFinder placeFinder;

    public RecordFindAllByPlaceResponse getRecordsByPlace(Long kakaoPlaceId) {

        Place place = placeFinder.findByKakaoPlaceId(kakaoPlaceId);
        List<Record> records = recordRepository.findAllByPlaceAndIsPublicTrue(place);

        List<RecordFindByPlaceResponse> responseList = records.stream()
                .map(record -> {
                    List<String> tags = recordTagRepository.findAllByRecord(record).stream()
                            .map(RecordTag::getContent)
                            .toList();

                    return RecordFindByPlaceResponse.from(record, tags);
                })
                .toList();

        return RecordFindAllByPlaceResponse.builder()
                .records(responseList)
                .build();
    }
}
