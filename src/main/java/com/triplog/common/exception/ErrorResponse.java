package com.triplog.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private LocalDateTime time;
    private HttpStatus status;
    private String message;
    private String requestURI;

    @Builder
    private ErrorResponse(LocalDateTime time, HttpStatus status, String message, String requestURI) {
        this.time = time;
        this.status = status;
        this.message = message;
        this.requestURI = requestURI;
    }

    public static ErrorResponse of(ErrorCode errorCode, String requestURI) {
        return ErrorResponse.builder()
                .time(LocalDateTime.now())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .requestURI(requestURI)
                .build();
    }
}
