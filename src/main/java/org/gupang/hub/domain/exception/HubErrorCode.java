package org.gupang.hub.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gupang.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HubErrorCode implements BaseErrorCode {
    INVALID_ROUTE(HttpStatus.BAD_REQUEST, "출발지와 도착지 허브가 같을 수 없습니다."),

    INVALID_LATITUDE(HttpStatus.BAD_REQUEST, "유효하지 않은 위도입니다."),
    INVALID_LONGITUDE(HttpStatus.BAD_REQUEST, "유효하지 않은 경도입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

}
