package com.triplog.Bookmark;

import com.triplog.Bookmark.domain.Bookmark;
import com.triplog.place.domain.Place;
import com.triplog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndPlace(User user, Place place);
    Optional<Bookmark> findByUserAndPlace(User user, Place place);
}
