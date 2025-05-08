package com.triplog.bookmark.service;

import com.triplog.bookmark.domain.Bookmark;
import com.triplog.bookmark.dto.BookmarkDeleteRequest;
import com.triplog.bookmark.dto.BookmarkSaveRequest;
import com.triplog.bookmark.repository.BookmarkRepository;
import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.place.PlaceFinder;
import com.triplog.place.domain.Place;
import com.triplog.user.UserFinder;
import com.triplog.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PlaceFinder placeFinder;
    private final UserFinder userFinder;

    @Transactional
    public void save(String nickname, BookmarkSaveRequest request) {

        User user = userFinder.findByNickname(nickname);
        Place place = placeFinder.findByKakaoPlaceId(request.kakaoPlaceId());

        if(bookmarkRepository.existsByUserAndPlace(user, place)) {
            throw new CustomException(ErrorCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .place(place)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void delete(String nickname, BookmarkDeleteRequest request) {

        User user = userFinder.findByNickname(nickname);
        Place place = placeFinder.findByKakaoPlaceId(request.kakaoPlaceId());

        Bookmark bookmark = bookmarkRepository.findByUserAndPlace(user, place)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }
}
