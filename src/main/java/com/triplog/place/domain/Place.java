package com.triplog.place.domain;

import com.triplog.common.domain.BaseEntity;
import com.triplog.place.domain.enums.Category;
import com.triplog.place.dto.PlaceSaveRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double latitude;

    private double longitude;

    private String address;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long kakaoPlaceId;

    public static Place from(PlaceSaveRequest request) {
        return Place.builder()
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .category(request.category())
                .kakaoPlaceId(request.kakaoPlaceId())
                .build();
    }
}
