package com.triplog.place.service;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.place.repository.PlaceRepository;
import com.triplog.place.domain.Place;
import com.triplog.place.dto.PlaceSaveRequest;
import com.triplog.place.dto.PlaceSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceSaveResponse save(PlaceSaveRequest request) {

        if(placeRepository.existsByKakaoPlaceId(request.kakaoPlaceId())) {
            throw new CustomException(ErrorCode.PLACE_ALREADY_EXISTS);
        }

        Place place = Place.from(request);

        Place savedPlace = placeRepository.save(place);

        return PlaceSaveResponse.builder()
                .placeId(savedPlace.getId())
                .build();
    }
}
