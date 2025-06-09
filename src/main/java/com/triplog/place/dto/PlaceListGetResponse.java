package com.triplog.place.dto;

import com.triplog.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceListGetResponse {

    @Schema(description = "Number of Page")
    private Integer page;

    @Schema(description = "is First Page")
    private Boolean isFirst;

    @Schema(description = "is Last Page")
    private Boolean isLast;

    @Schema(description = "has Next Page")
    private Boolean hasNext;

    @Schema(description = "has Prev Page")
    private Boolean hasPrev;

    @Schema(description = "Count")
    private Integer count;

    @Builder.Default
    @Schema(description = "List of Places")
    private List<PlaceGetResponse> places = new ArrayList<>();

}
