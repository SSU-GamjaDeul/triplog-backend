package com.triplog.trip;
import com.triplog.trip.dto.TripCreateRequest;
import com.triplog.trip.dto.TripCreateResponse;
import com.triplog.trip.dto.TripDetailResponse;
import com.triplog.trip.dto.TripFindByUserResponse;
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
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Trip", description = "Trip 관련 API입니다.")
public class TripController {

    private final TripService tripService;

    @PostMapping("/users/trips")
    @Operation(summary = "여행 생성", description = "여행에 대한 정보를 입력받고 새로 생성합니다.")
    public ResponseEntity<TripCreateResponse> createTrip(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid TripCreateRequest request) {
        String username = userDetails.getUsername();
        TripCreateResponse response = tripService.createTrip(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/trips")
    @Operation(summary = "여행 목록 조회", description = "사용자의 여행 목록을 조회합니다.")
    public ResponseEntity<TripFindByUserResponse> getTripsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        TripFindByUserResponse response = tripService.getTripsByUser(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/trip/{trip_id}")
    @Operation(summary = "여행 상세 조회", description = "각 여행의 상세 정보를 조회합니다.")
    public ResponseEntity<TripDetailResponse> getTripDetail(@PathVariable Long trip_id) {
        TripDetailResponse response = tripService.getTripDetail(trip_id);
        return ResponseEntity.ok(response);
    }
}