package com.triplog.record.controller;

import com.triplog.record.dto.RecordCreateDto;
import com.triplog.record.dto.RecordFindAllByPlaceResponse;
import com.triplog.record.dto.RecordUpdateDto;
import com.triplog.record.service.RecordService;
import com.triplog.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Record", description = "Record 관련 API입니다.")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    @Operation(summary = "장소 기반 기록 목록 조회", description = "카카오 장소 id를 기반으로 해당 장소에 대한 모든 유저의 공개된 기록 목록을 생성합니다.")
    public ResponseEntity<RecordFindAllByPlaceResponse> getRecordsByPlace(@RequestParam Long kakaoPlaceId) {

        RecordFindAllByPlaceResponse response = recordService.getRecordsByPlace(kakaoPlaceId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/trips/{trip_id}")
    @Operation(summary = "기록 생성", description = "여행의 기록을 생성합니다.")
    public ResponseEntity<?> createRecord(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long trip_id, @RequestBody RecordCreateDto recordCreateDto) {
        String nickname=userDetails.getUsername();
        recordService.createRecord(nickname,trip_id,recordCreateDto);
        return ResponseEntity.ok("기록이 생성되었습니다.");
    }

    @PatchMapping("/{record_id}")
    @Operation(summary = "기록 수정", description = "여행의 기록을 수정합니다.")
    public ResponseEntity<?> updateRecord(@PathVariable Long record_id, @RequestBody RecordUpdateDto recordUpdateDto) {
        recordService.updateRecord(record_id,recordUpdateDto);
        return ResponseEntity.ok("기록이 수정되었습니다.");
    }

    @DeleteMapping("/{record_id}")
    @Operation(summary = "기록 삭제", description = "여행의 기록을 삭제합니다.")
    public ResponseEntity<?> deleteRecord(@PathVariable Long record_id) {
        recordService.deleteRecord(record_id);
        return ResponseEntity.ok("기록이 삭제되었습니다.");
    }
}