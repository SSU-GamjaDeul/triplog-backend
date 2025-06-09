package com.triplog.place.service;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.place.dto.PlaceGetResponse;
import com.triplog.place.dto.PlaceListGetResponse;
import com.triplog.place.repository.PlaceRepository;
import com.triplog.place.domain.Place;
import com.triplog.place.dto.PlaceSaveRequest;
import com.triplog.place.dto.PlaceSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

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

    public Optional<PlaceListGetResponse> findByAddressContains (
            int page,
            int limit,
            String addressProvince,
            String addressCity
    ) {

        // Querying
        PageRequest pageRequest = PageRequest.of(page, Math.min(100, limit), Sort.by("id"));

        String addressLikeQuery = "";
        if (addressProvince != null && !addressProvince.isEmpty()) addressLikeQuery += addressProvince;
        if (addressCity != null && !addressCity.isEmpty()) addressLikeQuery += " " + addressCity;

        Slice<Place> placeSlice = null;
        if (addressLikeQuery.isEmpty()) placeSlice = placeRepository.findAllBy(pageRequest);
        else placeSlice = placeRepository.findAllByAddressContains(pageRequest, addressLikeQuery);

        // Build DTO
        if (placeSlice == null || placeSlice.getNumberOfElements() == 0) return Optional.empty();

        PlaceListGetResponse placeListGetResponse = PlaceListGetResponse.builder()
                .page(placeSlice.getNumber())
                .isFirst(placeSlice.isFirst())
                .isLast(placeSlice.isLast())
                .hasPrev(placeSlice.hasPrevious())
                .hasNext(placeSlice.hasNext())
                .count(placeSlice.getNumberOfElements())
                .places(placeSlice.get().map(PlaceGetResponse::new).toList())
                .build();
        return Optional.of(placeListGetResponse);

    }

}
