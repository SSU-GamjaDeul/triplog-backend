package com.triplog.place.dto;

import com.triplog.place.domain.Place;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceGetResponse {

    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    private String address;

    private Long kakaoPlaceId;

    public PlaceGetResponse (
            Place place
    ) {
        this.id = place.getId();
        this.name = place.getName();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
        this.address = place.getAddress();
        this.kakaoPlaceId = place.getKakaoPlaceId();
    }

}
