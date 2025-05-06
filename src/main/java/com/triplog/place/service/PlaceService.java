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

        // 중복 체크 : 이름과 장소가 모두 일치하는 장소가 이미 DB에 있는 경우, 에러를 반환한다.
        boolean isExistingPlace = placeRepository.existsByNameAndAddress(request.name(), request.address());

        if(isExistingPlace) {
            throw new CustomException(ErrorCode.PLACE_ALREADY_EXISTS);
        }

        Place place = Place.builder()
                .name(request.name())
                .address(request.address())
                .build();

        Place savedPlace = placeRepository.save(place);

        return PlaceSaveResponse.builder()
                .placeId(savedPlace.getId())
                .build();
    }
}
