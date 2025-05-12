package com.triplog.bookmark.repository;

import com.triplog.bookmark.domain.Bookmark;
import com.triplog.place.domain.Place;
import com.triplog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndPlace(User user, Place place);
    Optional<Bookmark> findByUserAndPlace(User user, Place place);

    @Query("SELECT b FROM Bookmark b JOIN FETCH b.place WHERE b.user = :user AND b.place IN :places")
    List<Bookmark> findAllByUserAndPlaceIn(User user, List<Place> places);
}
