package com.triplog.place;

import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.place.domain.Place;
import com.triplog.place.domain.enums.Category;
import com.triplog.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceFinder {

    private final PlaceRepository placeRepository;

    public Place findByKakaoPlaceId(Long kakaoPlaceId) {
        return placeRepository.findByKakaoPlaceId(kakaoPlaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));
    }

    public List<Place> findAllByLatitudeAndLongitudeAndCategory(double minLat,
                                                                double maxLat,
                                                                double minLng,
                                                                double maxLng,
                                                                List<Category> categories) {

        if (categories == null || categories.isEmpty()) {
            return placeRepository.findAllByLatitudeBetweenAndLongitudeBetween(
                    minLat, maxLat, minLng, maxLng);
        }

        return placeRepository.findAllByLatitudeBetweenAndLongitudeBetweenAndCategoryIn(
                minLat, maxLat, minLng, maxLng, categories);
    }
}
