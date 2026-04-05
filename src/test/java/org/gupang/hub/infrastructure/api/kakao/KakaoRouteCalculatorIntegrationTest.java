package org.gupang.hub.infrastructure.api.kakao;

import org.gupang.hub.domain.route.RouteEstimate;
import org.gupang.hub.domain.vo.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class KakaoRouteCalculatorIntegrationTest {

    @Autowired
    private KakaoRouteCalculator kakaoRouteCalculator;

    @Test
    @DisplayName("성공: 카카오 API를 호출하여 경로 데이터 조회")
    void calculate_RealApiCall_Success() {
        // given
        Coordinate start = new Coordinate(37.3947, 127.1111);
        Coordinate end = new Coordinate(37.4020, 127.1086);

        // when
        RouteEstimate estimate = kakaoRouteCalculator.calculate(start, end);

        // then
        System.out.println("예상 소요 시간(초): " + estimate.estimateDuration());
        System.out.println("예상 이동 거리(m): " + estimate.estimateDistance());

        assertThat(estimate.estimateDuration()).isPositive();
        assertThat(estimate.estimateDistance()).isPositive();
    }
}
