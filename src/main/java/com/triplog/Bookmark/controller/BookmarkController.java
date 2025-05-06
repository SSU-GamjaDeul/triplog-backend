package com.triplog.Bookmark.controller;

import com.triplog.Bookmark.service.BookmarkService;
import com.triplog.Bookmark.dto.BookmarkDeleteRequest;
import com.triplog.Bookmark.dto.BookmarkSaveRequest;
import com.triplog.user.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid BookmarkSaveRequest request) {

        String nickname = userDetails.getUsername();
        bookmarkService.save(nickname, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid BookmarkDeleteRequest request) {

        String nickname = userDetails.getUsername();
        bookmarkService.delete(nickname, request);

        return ResponseEntity.ok().build();
    }
}
