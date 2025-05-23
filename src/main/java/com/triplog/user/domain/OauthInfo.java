package com.triplog.user.domain;

import com.triplog.user.domain.enums.OauthProvider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;
}
