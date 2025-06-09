package com.triplog.place.repository;

import com.triplog.place.domain.Place;
import com.triplog.place.domain.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    boolean existsByKakaoPlaceId(Long kakaoPlaceId);

    List<Place> findAllByLatitudeBetweenAndLongitudeBetween(
            double minLat, double maxLat, double minLng, double maxLng);

    List<Place> findAllByLatitudeBetweenAndLongitudeBetweenAndCategoryIn(
            double minLat, double maxLat, double minLng, double maxLng, List<Category> categories);

    Optional<Place> findByKakaoPlaceId(Long kakaoPlaceId);

    Slice<Place> findAllByAddressContains (Pageable pageable, String address);
    Slice<Place> findAllBy (Pageable pageable);

}
