package com.triplog.place.controller;
import com.triplog.place.service.PlaceService;
import com.triplog.place.dto.PlaceSaveRequest;
import com.triplog.place.dto.PlaceSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Place", description = "Place 관련 API입니다.")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    @Operation(summary = "장소 등록", description = "Kakao Map에서 제공하는 특정 장소에 대한 정보를 Trip Log DB에 저장합니다.")
    public ResponseEntity<PlaceSaveResponse> save(@RequestBody @Valid PlaceSaveRequest request) {

        PlaceSaveResponse response = placeService.save(request);

        return ResponseEntity.ok(response);
    }
}
