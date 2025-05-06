package com.triplog.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	//Internal Server Error
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 생겼습니다."),

	// Client Error
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "적절하지 않은 HTTP 메소드입니다."),
	INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "요청 값의 타입이 잘못되었습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "적절하지 않은 값입니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
	MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다."),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자의 정보를 찾을 수 없습니다."),
	DUPLICATE_USER(HttpStatus.CONFLICT,"존재하는 닉네임 입니다."),

	// Trip

	// Record

	// Bookmark
	BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크 정보를 찾을 수 없습니다."),

	// Place
	PLACE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 장소 정보입니다."),
	PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "DB 내에서 해당 장소 정보를 찾을 수 없습니다."),

	// JWT
	TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰 정보를 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
