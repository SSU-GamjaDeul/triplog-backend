package com.triplog.trip;
import com.triplog.place.dto.PlaceSaveResponse;
import com.triplog.trip.dto.TripCreateRequest;
import com.triplog.trip.dto.TripCreateResponse;
import com.triplog.user.jwt.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;

    @PostMapping("/users/trips")
    public ResponseEntity<TripCreateResponse> createTrip(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid TripCreateRequest request) {
        Long userId = userDetails.getUser().getId();
        TripCreateResponse response = tripService.createTrip(request, userId);
        return ResponseEntity.ok(response);
    }
}
