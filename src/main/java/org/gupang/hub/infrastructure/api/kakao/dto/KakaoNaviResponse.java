package org.gupang.hub.infrastructure.api.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.gupang.hub.global.exception.HubErrorCode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoNaviResponse(
        List<Route> routes
) {
    private static final Integer SUCCESS_RESULT_CODE = 0;

    public record Route(
            Integer resultCode,
            Summary summary
    ) {}

    public record Summary(
            Integer distance,
            Integer duration
    ) {}

    public Integer getDistance() {
        return getValidatedRoute().summary().distance();
    }

    public Integer getDuration() {
        return getValidatedRoute().summary().duration();
    }

    private Route getValidatedRoute() {
        if (routes == null || routes.isEmpty()) {
            throw new IllegalStateException(HubErrorCode.KAKAO_API_NO_ROUTES.getMessage());
        }

        Route firstRoute = routes.get(0);
        if (!SUCCESS_RESULT_CODE.equals(firstRoute.resultCode())) {
            throw new IllegalStateException(
                    HubErrorCode.KAKAO_API_FAILURE.getMessage() + " [결과 코드: " + firstRoute.resultCode() + "]"
            );
        }

        return firstRoute;
    }
}
