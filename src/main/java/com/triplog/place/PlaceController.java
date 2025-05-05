package com.triplog.place;
import com.triplog.place.dto.PlaceSaveRequest;
import com.triplog.place.dto.PlaceSaveResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    // 장소 등록
    @PostMapping
    public ResponseEntity<PlaceSaveResponse> save(@RequestBody @Valid PlaceSaveRequest request) {

        PlaceSaveResponse response = placeService.save(request);

        return ResponseEntity.ok(response);
    }
}
