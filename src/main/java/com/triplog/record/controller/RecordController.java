package com.triplog.record.controller;

import com.triplog.place.domain.enums.Category;
import com.triplog.record.dto.*;
import com.triplog.record.service.RecordService;
import com.triplog.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Record", description = "Record 관련 API입니다.")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/place")
    @Operation(summary = "장소 기반 기록 목록 조회", description = "카카오 장소 id를 기반으로 해당 장소에 대한 모든 유저의 공개된 기록 목록을 조회합니다.")
    public ResponseEntity<RecordFindAllByPlaceResponse> getRecordsByPlace(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long kakaoPlaceId) {
        String nickname = userDetails.getUsername();
        RecordFindAllByPlaceResponse response = recordService.getRecordsByPlace(nickname, kakaoPlaceId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/trips/{trip_id}")
    @Operation(summary = "기록 생성", description = "여행의 기록을 생성합니다.")
    public ResponseEntity<String> createRecord(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long trip_id, @RequestBody RecordCreateRequest recordCreateDto) {
        String nickname=userDetails.getUsername();
        recordService.createRecord(nickname,trip_id,recordCreateDto);
        return ResponseEntity.ok("기록이 생성되었습니다.");
    }

    @PatchMapping("/{record_id}")
    @Operation(summary = "기록 수정", description = "여행의 기록을 수정합니다.")
    public ResponseEntity<String> updateRecord(@PathVariable Long record_id, @RequestBody RecordUpdateRequest recordUpdateDto) {
        recordService.updateRecord(record_id,recordUpdateDto);
        return ResponseEntity.ok("기록이 수정되었습니다.");
    }

    @DeleteMapping("/{record_id}")
    @Operation(summary = "기록 삭제", description = "여행의 기록을 삭제합니다.")
    public ResponseEntity<String> deleteRecord(@PathVariable Long record_id) {
        recordService.deleteRecord(record_id);
        return ResponseEntity.ok("기록이 삭제되었습니다.");
    }

    @GetMapping("/location")
    @Operation(summary = "위치 기반 기록 목록 조회 (카테고리 필터 포함)", description = "위도·경도 범위와 선택한 카테고리에 해당하는 장소들을 기준으로 사용자의 기록 목록을 조회합니다.")
    public ResponseEntity<RecordFindAllByLocationResponse> getRecordsByLocation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                                @RequestParam double minLat,
                                                                                @RequestParam double maxLat,
                                                                                @RequestParam double minLng,
                                                                                @RequestParam double maxLng,
                                                                                @RequestParam(required = false) List<Category> categories) {

        String nickname = userDetails.getUsername();
        RecordFindAllByLocationResponse response = recordService.getRecordsByLocation(nickname, minLat, maxLat, minLng, maxLng, categories);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @Operation(summary = "유저의 기록 목록 조회", description = "유저의 access token을 기반으로 해당 유저의 모든 기록을 조회합니다.")
    public ResponseEntity<RecordFindAllByUserResponse> getRecordsByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username=customUserDetails.getUsername();
        RecordFindAllByUserResponse response= recordService.getRecordsByUser(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{trip_id}")
    @Operation(summary = "여행 기반 기록 목록 조회", description = "여행에 대해 작성된 모든 기록을 조회합니다.")
    public ResponseEntity<RecordFindAllByTripResponse> getRecordsByTrip(@PathVariable Long trip_id) {
        RecordFindAllByTripResponse response=recordService.getRecordsByTrip(trip_id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{record_id}")
    @Operation(summary = "기록 상세 조회", description = "해당 기록에 대한 상세 정보를 조회합니다.")
    public ResponseEntity<RecordDetailResponse> getRecordDetail(@PathVariable Long record_id){
        RecordDetailResponse response=recordService.getRecordDetail(record_id);
        return ResponseEntity.ok(response);

    }

}