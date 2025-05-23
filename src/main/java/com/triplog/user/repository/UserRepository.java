package com.triplog.user.repository;

import com.triplog.user.domain.OauthInfo;
import com.triplog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    Optional<User> findByNickname(String nickname);
    Optional<User> findByOauthInfo(OauthInfo oauthInfo);
    Optional<User> findByEmail(String email);

    boolean existsByOauthInfo(OauthInfo oauthInfo);
}
