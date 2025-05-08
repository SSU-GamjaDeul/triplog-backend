package com.triplog.record.service;

import com.triplog.place.PlaceFinder;
import com.triplog.place.domain.Place;
import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordTag;
import com.triplog.record.dto.RecordCreateDto;
import com.triplog.record.dto.RecordFindAllByPlaceResponse;
import com.triplog.record.repository.RecordRepository;
import com.triplog.record.repository.RecordTagRepository;
import com.triplog.trip.TripFinder;
import com.triplog.trip.domain.Trip;
import com.triplog.user.UserFinder;
import com.triplog.user.domain.User;
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
    private final UserFinder userFinder;
    private final TripFinder tripFinder;

    public RecordFindAllByPlaceResponse getRecordsByPlace(Long kakaoPlaceId) {

        Place place = placeFinder.findByKakaoPlaceId(kakaoPlaceId);
        List<Record> records = recordRepository.findAllByPlaceAndIsPublicTrueOrderByDateDesc(place);

        List<RecordFindAllByPlaceResponse.Item> responseList = records.stream()
                .map(record -> {
                    List<RecordTag> tags = recordTagRepository.findAllByRecord(record);
                    return RecordFindAllByPlaceResponse.Item.from(record, tags);
                })
                .toList();

        return RecordFindAllByPlaceResponse.builder()
                .records(responseList)
                .build();
    }

    @Transactional
    public void createRecord(String nickname, Long tripId, RecordCreateDto recordCreateDto) {
        User user=userFinder.findByNickname(nickname);
        Trip trip = tripFinder.findByTripId(tripId);
        Place place = placeFinder.findByKakaoPlaceId(recordCreateDto.getKakaoPlaceId());

        Record record=Record.builder()
                .user(user)
                .title(recordCreateDto.getTitle())
                .memo(recordCreateDto.getMemo())
                .date(recordCreateDto.getDate())
                .isPublic(recordCreateDto.is_public())
                .build();
        recordRepository.save(record);
    }
}
