package com.triplog.user.repository;

import com.triplog.user.domain.User;
import com.triplog.user.domain.UserVibe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVibeRepository extends JpaRepository<UserVibe,Long> {
    List<UserVibe> findByUser(User user);
    void deleteAllByUser(User user);
}
