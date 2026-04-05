package org.gupang.hub.domain.route;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RouteEstimateTest {

    Integer validDuration = 36000;
    Integer validDistance = 1000;

    @Test
    @DisplayName("성공: 정상적인 duration, distance가 주어진 경우 RouteEstimate 생성")
    void createRouteEstimate_Success() {
        // given & when
        RouteEstimate estimate = new RouteEstimate(validDuration, validDistance);

        // then
        assertThat(estimate.estimateDuration()).isEqualTo(validDuration);
        assertThat(estimate.estimateDistance()).isEqualTo(validDistance);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("실패: 이동 거리가 0 이하인 경우 IllegalArgumentException 발생")
    void createRouteEstimate_InvalidDistance(int invalidDistance) {
        // when & then
        assertThatThrownBy(() -> new RouteEstimate(validDuration, invalidDistance))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50})
    @DisplayName("실패: 소요 시간이 0 이하인 경우 IllegalArgumentException 발생")
    void createRouteEstimate_InvalidDuration(int invalidDuration) {
        // when & then
        assertThatThrownBy(() -> new RouteEstimate(invalidDuration, validDistance))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
