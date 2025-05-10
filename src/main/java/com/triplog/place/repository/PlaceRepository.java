package com.triplog.place.repository;

import com.triplog.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    boolean existsByNameAndAddress(String name, String Address);
    List<Place> findAllByLatitudeBetweenAndLongitudeBetween(double minLat, double maxLat, double minLng, double maxLng);

    Optional<Place> findByKakaoPlaceId(Long kakaoPlaceId);
}
