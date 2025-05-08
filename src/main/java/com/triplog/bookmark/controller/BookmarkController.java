package com.triplog.bookmark.controller;

import com.triplog.bookmark.service.BookmarkService;
import com.triplog.bookmark.dto.BookmarkDeleteRequest;
import com.triplog.bookmark.dto.BookmarkSaveRequest;
import com.triplog.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Bookmark", description = "Bookmark 관련 API입니다.")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "북마크 생성", description = "카카오 장소 id를 기반으로 해당 장소에 대한 북마크를 생성합니다.")
    public ResponseEntity<Void> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid BookmarkSaveRequest request) {

        String nickname = userDetails.getUsername();
        bookmarkService.save(nickname, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "북마크 삭제", description = "카카오 장소 id를 기반으로 해당 장소에 대한 북마크를 삭제합니다.")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @RequestBody @Valid BookmarkDeleteRequest request) {

        String nickname = userDetails.getUsername();
        bookmarkService.delete(nickname, request);

        return ResponseEntity.ok().build();
    }
}
