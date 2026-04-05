package org.gupang.hub.infrastructure.api.kakao;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.domain.route.RouteDistanceCalculator;
import org.gupang.hub.domain.route.RouteEstimate;
import org.gupang.hub.domain.vo.Coordinate;
import org.gupang.hub.infrastructure.api.kakao.dto.KakaoNaviResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoRouteCalculator implements RouteDistanceCalculator {

    private final KakaoNaviClient kakaoNaviClient;

    @Value("${kakao.rest-api-key}")
    private String apiKey;

    private static final String CONTENT_TYPE = "application/json";
    private static final String AUTH_PREFIX = "KakaoAK ";

    @Override
    public RouteEstimate calculate(Coordinate start, Coordinate end) {
        String origin = formatCoordinate(start);
        String destination = formatCoordinate(end);

        KakaoNaviResponse response = kakaoNaviClient.getDirections(
                AUTH_PREFIX + apiKey,
                CONTENT_TYPE,
                origin,
                destination
        );

        return new RouteEstimate(response.getDuration(), response.getDistance());
    }

    private String formatCoordinate(Coordinate coordinate) {
        return String.format("%s,%s", coordinate.getLongitude(), coordinate.getLatitude());
    }
}
