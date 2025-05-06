package com.triplog.place;

import com.triplog.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    boolean existsByNameAndAddress(String name, String Address);
    Optional<Place> findByKakaoPlaceId(Long kakaoPlaceId);
}
