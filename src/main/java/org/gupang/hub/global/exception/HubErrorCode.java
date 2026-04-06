package org.gupang.hub.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gupang.common.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HubErrorCode implements BaseErrorCode {
    // Hub
    INVALID_LATITUDE(HttpStatus.BAD_REQUEST, "유효하지 않은 위도입니다."),
    INVALID_LONGITUDE(HttpStatus.BAD_REQUEST, "유효하지 않은 경도입니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다."),
    DUPLICATE_HUB_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 허브명입니다."),

    // Hub Route
    INVALID_HUB_ROUTE(HttpStatus.BAD_REQUEST, "유효하지 않은 경로 요청입니다."),
    HUB_ROUTE_FINDER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "허브 간 이동 경로 탐색 알고리즘을 로드할 수 없습니다."),
    HUB_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "허브 간 이동 경로를 찾을 수 없습니다."),

    // Kakao Navi API
    KAKAO_API_NO_ROUTES(HttpStatus.BAD_GATEWAY, "카카오 API 응답에 경로 정보가 없습니다."),
    KAKAO_API_FAILURE(HttpStatus.BAD_GATEWAY, "카카오 API 호출에 실패했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

}
