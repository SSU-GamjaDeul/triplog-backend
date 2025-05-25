package com.triplog.trip;
import com.triplog.trip.dto.*;
import com.triplog.user.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Trip", description = "Trip 관련 API입니다.")
public class TripController {

    private final TripService tripService;

    @PostMapping
    @Operation(summary = "여행 생성", description = "여행에 대한 정보를 입력받고 새로 생성합니다.")
    public ResponseEntity<TripCreateResponse> createTrip(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid TripCreateRequest request) {
        String username = userDetails.getUsername();
        TripCreateResponse response = tripService.createTrip(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "여행 목록 조회", description = "사용자의 여행 목록을 조회합니다.")
    public ResponseEntity<TripFindByUserResponse> getTripsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        TripFindByUserResponse response = tripService.getTripsByUser(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{tripId}")
    @Operation(summary = "여행 상세 조회", description = "각 여행의 상세 정보를 조회합니다.")
    public ResponseEntity<TripDetailResponse> getTripDetail(@PathVariable Long tripId) {
        TripDetailResponse response = tripService.getTripDetail(tripId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{tripId}/invite")
    @Operation(summary = "동반자 초대", description = "사용자의 닉네임으로 여행에 동반자를 초대합니다.")
    public ResponseEntity<String> inviteTrip(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long tripId, @RequestBody @Valid TripInviteRequest request) {
        String username = userDetails.getUsername();
        tripService.inviteTrip(username, tripId, request);
        return ResponseEntity.ok("동반자 초대 완료");
    }

    @GetMapping("/invite")
    @Operation(summary = "동반자 초대 조회", description = "사용자가 받은 여행 초대를 조회합니다.")
    public ResponseEntity<TripInviteFindByUserResponse> getTripInvitesByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        TripInviteFindByUserResponse response = tripService.getTripInvitesByUser(username);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{tripId}/invite/accept")
    @Operation(summary = "동반자 초대 수락", description = "사용자가 받은 여행 초대를 수락합니다.")
    public ResponseEntity<String> acceptInvite(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long tripId) {
        String username = userDetails.getUsername();
        tripService.acceptInvite(username, tripId);
        return ResponseEntity.ok("초대 수락 완료");
    }

    @DeleteMapping("{tripId}/invite/refuse")
    @Operation(summary = "동반자 초대 거절", description = "사용자가 받은 여행 초대를 거절합니다.")
    public ResponseEntity<String> refuseInvite(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long tripId) {
        String username = userDetails.getUsername();
        tripService.refuseInvite(username, tripId);
        return ResponseEntity.ok("초대 거절 완료");
    }

    @PatchMapping("{tripId}")
    @Operation(summary = "여행 수정", description = "각 여행의 정보를 수정합니다.")
    public ResponseEntity<String> updateTrip(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long tripId, @RequestBody @Valid TripUpdateRequest request) {
        String username = userDetails.getUsername();
        tripService.updateTrip(username, tripId, request);
        return ResponseEntity.ok("여행 수정 완료");
    }
}