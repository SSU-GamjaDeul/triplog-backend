package com.triplog.place.controller;
import com.triplog.place.dto.PlaceListGetResponse;
import com.triplog.place.service.PlaceService;
import com.triplog.place.dto.PlaceSaveRequest;
import com.triplog.place.dto.PlaceSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping()
    @Operation(
            summary = "장소 목록 가져오기",
            description = "province(시/도), city(시/군/구) 와 일치하는 등록된 장소의 리스트를 가져옵니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK - Successful", content = {@Content(schema = @Schema(implementation = PlaceListGetResponse.class))}),
                    @ApiResponse(responseCode = "204", description = "NO CONTENT - Successful", content = {@Content}),
            }
    )
    public ResponseEntity<PlaceListGetResponse> getPlaceList (
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city
    ) {
        Optional<PlaceListGetResponse> resBody = placeService.findByAddressContains(page, limit, province, city);

        if (resBody.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(resBody.get());
    }

}
