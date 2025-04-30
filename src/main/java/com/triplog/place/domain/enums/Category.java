package com.triplog.place.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {

    TOURIST_SPOT("관광지"),
    RESTAURANT("음식점"),
    CAFE_BAKERY("카페/베이커리"),
    ACCOMMODATION("숙소"),
    ETC("기타");

    private final String description;
}
