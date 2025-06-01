package com.triplog.record.service;

import com.triplog.place.PlaceFinder;
import com.triplog.place.domain.Place;
import com.triplog.place.domain.enums.Category;
import com.triplog.record.RecordFinder;
import com.triplog.record.domain.Record;
import com.triplog.record.domain.RecordImage;
import com.triplog.record.domain.RecordTag;
import com.triplog.record.dto.*;
import com.triplog.record.repository.RecordImageRepository;
import com.triplog.record.repository.RecordRepository;
import com.triplog.record.repository.RecordTagRepository;
import com.triplog.trip.TripFinder;
import com.triplog.trip.TripParticipantFinder;
import com.triplog.trip.TripService;
import com.triplog.trip.domain.Trip;
import com.triplog.trip.domain.TripParticipant;
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
    private final RecordImageRepository recordImageRepository;
    private final PlaceFinder placeFinder;
    private final UserFinder userFinder;
    private final TripFinder tripFinder;
    private final RecordFinder recordFinder;
    private final TripParticipantFinder tripParticipantFinder;
    private final TripService tripService;

    public RecordFindAllByPlaceResponse getRecordsByPlace(String nickname, Long kakaoPlaceId) {

        User user = userFinder.findByNickname(nickname);
        Place place = placeFinder.findByKakaoPlaceId(kakaoPlaceId);
        List<Record> records = recordRepository.findAllByPlaceAndIsPublicTrueOrderByDateDesc(place);

        // 사용자가 참여 중인 Trip 목록 조회
        List<Trip> myTrips = tripParticipantFinder.findAllByUserAndIsAcceptedTrue(user)
                .stream()
                .map(TripParticipant::getTrip)
                .toList();

        List<RecordFindAllByPlaceResponse.Item> responseList = records.stream()
                .map(record -> {
                    List<RecordTag> tags = recordTagRepository.findAllByRecord(record);
                    boolean isMyTripRecord = myTrips.contains(record.getTrip());
                    return RecordFindAllByPlaceResponse.Item.from(record, tags, isMyTripRecord);
                })
                .toList();

        return RecordFindAllByPlaceResponse.builder()
                .records(responseList)
                .build();
    }

    @Transactional
    public void createRecord(String nickname, Long tripId, RecordCreateRequest recordCreateDto) {
        User user=userFinder.findByNickname(nickname);
        Trip trip = tripFinder.findByTripId(tripId);
        Place place = placeFinder.findByKakaoPlaceId(recordCreateDto.kakaoPlaceId());

        Record record=Record.builder()
                .user(user)
                .place(place)
                .trip(trip)
                .title(recordCreateDto.title())
                .memo(recordCreateDto.memo())
                .date(recordCreateDto.date())
                .isPublic(recordCreateDto.isPublic())
                .build();
        List<String> tagContents=recordCreateDto.tags();

        for(String tagContent:tagContents){
            RecordTag recordTag=RecordTag.builder()
                    .record(record)
                    .content(tagContent)
                    .build();
            recordTagRepository.save(recordTag);
        }
        List<String> imageUrls=recordCreateDto.imageUrl();
        for(String images: imageUrls){
            RecordImage recordImage=RecordImage.builder()
                    .record(record)
                    .imageUrl(images)
                    .build();
            recordImageRepository.save(recordImage);
        }
        recordRepository.save(record);
        tripService.updateTripDate(record.getTrip());
    }

    @Transactional
    public void updateRecord(Long recordId, RecordUpdateRequest recordUpdateDto) {
        Record record=recordFinder.findByRecordId(recordId);
        record.update(
                recordUpdateDto.title(),
                recordUpdateDto.memo(),
                recordUpdateDto.date(),
                recordUpdateDto.isPublic()
        );
        tripService.updateTripDate(record.getTrip());

        recordTagRepository.deleteAllByRecord(record);
        List<String> newTags = recordUpdateDto.tags();
        if (newTags != null) {
            for (String tag : newTags) {
                RecordTag recordTag = RecordTag.builder()
                        .record(record)
                        .content(tag)
                        .build();
                recordTagRepository.save(recordTag);
            }
        }

        recordImageRepository.deleteAllByRecord(record);
        List<String> newImageUrls = recordUpdateDto.imageUrls();
        if (newImageUrls != null) {
            for (String imageUrl : newImageUrls) {
                RecordImage recordImage=RecordImage.builder()
                        .record(record)
                        .imageUrl(imageUrl)
                        .build();
                recordImageRepository.save(recordImage);
            }
        }
    }

    @Transactional
    public void deleteRecord(Long recordId) {
        Record record=recordFinder.findByRecordId(recordId);
        Trip trip = record.getTrip();
        recordImageRepository.deleteAllByRecord(record);
        recordTagRepository.deleteAllByRecord(record);
        recordRepository.delete(record);
        tripService.updateTripDate(trip);
    }

    public RecordFindAllByLocationResponse getRecordsByLocation(String nickname,
                                                                double minLat,
                                                                double maxLat,
                                                                double minLng,
                                                                double maxLng,
                                                                List<Category> categories) {

        User user = userFinder.findByNickname(nickname);

        // 1. 사용자가 참여한 Trip 목록 조회
        List<Trip> myTrips = tripParticipantFinder.findAllByUserAndIsAcceptedTrue(user)
                .stream()
                .map(TripParticipant::getTrip)
                .toList();

        // 2. 위경도 + 카테고리 조건을 만족하는 장소 조회
        List<Place> places = placeFinder.findAllByLatitudeAndLongitudeAndCategory(minLat, maxLat, minLng, maxLng, categories);

        // 3. Trip과 Place 조건을 만족하는 기록들 조회
        List<Record> records = recordRepository.findAllByTripInAndPlaceIn(myTrips, places);

        List<RecordFindAllByLocationResponse.Item> responseList = records.stream()
                .map(RecordFindAllByLocationResponse.Item::from)
                .toList();

        return RecordFindAllByLocationResponse.builder()
                .records(responseList)
                .build();
    }


    public RecordFindAllByUserResponse getRecordsByUser(String username) {
        User user = userFinder.findByNickname(username);
        List<Record> records=recordRepository.findAllByUser(user);
        List<RecordFindAllByUserResponse.Item> responseList=records.stream()
                .map(record -> {
                    List<RecordTag> tags=recordTagRepository.findAllByRecord(record);
                    return RecordFindAllByUserResponse.Item.from(record, tags);
                })
                .toList();
        return RecordFindAllByUserResponse.builder()
                .records(responseList)
                .build();
    }

    public RecordFindAllByTripResponse getRecordsByTrip(Long tripId) {
        Trip trip = tripFinder.findByTripId(tripId);
        List<Record> records=recordRepository.findAllByTrip(trip);
        List<RecordFindAllByTripResponse.Item> responseList=records.stream()
                .map(record -> {
                    List<RecordTag> tags=recordTagRepository.findAllByRecord(record);
                    List<RecordImage> images = recordImageRepository.findAllByRecord(record);
                    return RecordFindAllByTripResponse.Item.from(record, tags,images);
                })
                .toList();
        return RecordFindAllByTripResponse.builder()
                .records(responseList)
                .build();
    }

    public RecordDetailResponse getRecordDetail(Long recordId) {
        Record record=recordFinder.findByRecordId(recordId);
        List<RecordTag> tags=recordTagRepository.findAllByRecord(record);
        List<RecordImage> images=recordImageRepository.findAllByRecord(record);
        return RecordDetailResponse.from(record,tags,images);
    }
}
