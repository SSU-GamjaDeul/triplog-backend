package com.triplog.Bookmark;

import com.triplog.Bookmark.domain.Bookmark;
import com.triplog.Bookmark.dto.BookmarkSaveRequest;
import com.triplog.common.exception.CustomException;
import com.triplog.common.exception.ErrorCode;
import com.triplog.place.PlaceRepository;
import com.triplog.place.domain.Place;
import com.triplog.user.domain.User;
import com.triplog.user.repository.UserRepository;
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
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String nickname, BookmarkSaveRequest request) {

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Place place = placeRepository.findByKakaoPlaceId(request.kakaoPlaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .place(place)
                .build();

        bookmarkRepository.save(bookmark);
    }
}
