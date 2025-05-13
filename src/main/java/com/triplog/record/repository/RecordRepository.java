package com.triplog.record.repository;

import com.triplog.place.domain.Place;
import com.triplog.record.domain.Record;
import com.triplog.trip.domain.Trip;
import com.triplog.user.domain.User;
import com.triplog.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByPlaceAndIsPublicTrueOrderByDateDesc(Place place);

    @Query("SELECT r FROM Record r JOIN FETCH r.place WHERE r.user = :user AND r.place IN :places")
    List<Record> findAllByUserAndPlaceIn(User user, List<Place> places);

    Optional<Record> findById(Long id);

    List<Record> findAllByUser(User user);
    List<Record> findAllByTrip(Trip trip);
}
