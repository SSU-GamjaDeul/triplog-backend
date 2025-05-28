package com.triplog.user.domain;

import com.triplog.common.domain.BaseEntity;
import com.triplog.user.domain.enums.Gender;
import jakarta.persistence.*;
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
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OauthInfo oauthInfo;

    private String accessToken;

    private String nickname;

    private String email;

    private String birthYear;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public void updateProfile(String birthYear, Gender gender) {
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public User update(String accessToken){
        this.accessToken = accessToken;
        return this;
    }
}
