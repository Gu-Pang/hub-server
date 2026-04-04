package org.gupang.hub.domain.entity;

import org.gupang.common.exception.CustomException;
import org.gupang.hub.global.exception.HubErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HubRouteTest {

    private static final Integer VALID_DURATION = 2400;
    private static final Double VALID_DISTANCE = 35.5;

    private Hub createMockHub() {
        Hub hub = mock(Hub.class);
        when(hub.getHubId()).thenReturn(UUID.randomUUID());
        return hub;
    }

    @Test
    @DisplayName("성공: 출발지와 도착지가 다르고 정상적인 수치가 주어지면 HubRoute 생성")
    void createHubRoute_Success() {
        // given
        Hub startHub = createMockHub();
        Hub endHub = createMockHub();

        // when
        HubRoute hubRoute = HubRoute.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(VALID_DURATION)
                .distance(VALID_DISTANCE)
                .build();

        // then
        assertThat(hubRoute.getStartHub()).isEqualTo(startHub);
        assertThat(hubRoute.getEndHub()).isEqualTo(endHub);
        assertThat(hubRoute.getDuration()).isEqualTo(VALID_DURATION);
        assertThat(hubRoute.getDistance()).isEqualTo(VALID_DISTANCE);
    }

    @Test
    @DisplayName("실패: 출발 허브가 null일 경우 NullPointerException 발생")
    void createHubRoute_Fail_StartHubIsNull() {
        // given
        Hub endHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(null)
                .endHub(endHub)
                .duration(VALID_DURATION)
                .distance(VALID_DISTANCE)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("출발 허브는 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 도착 허브가 null일 경우 NullPointerException 발생")
    void createHubRoute_Fail_EndHubIsNull() {
        // given
        Hub startHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(startHub)
                .endHub(null)
                .duration(VALID_DURATION)
                .distance(VALID_DISTANCE)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("도착 허브는 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 출발지와 도착지 허브의 ID가 같으면 CustomException 발생")
    void createHubRoute_Fail_SameHub() {
        // given
        Hub sameHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(sameHub)
                .endHub(sameHub)
                .duration(VALID_DURATION)
                .distance(VALID_DISTANCE)
                .build())
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(HubErrorCode.INVALID_HUB_ROUTE.getMessage());
    }

    @Test
    @DisplayName("실패: 소요 시간이 null일 경우 NullPointerException 발생")
    void createHubRoute_Fail_DurationIsNull() {
        // given
        Hub startHub = createMockHub();
        Hub endHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(null)
                .distance(VALID_DISTANCE)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("소요 시간은 필수 입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("실패: 소요 시간이 0 이하일 경우 IllegalArgumentException 발생")
    void createHubRoute_Fail_InvalidDuration(int invalidDuration) {
        // given
        Hub startHub = createMockHub();
        Hub endHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(invalidDuration)
                .distance(VALID_DISTANCE)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("소요 시간은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("실패: 이동 거리가 null일 경우 NullPointerException 발생")
    void createHubRoute_Fail_DistanceIsNull() {
        // given
        Hub startHub = createMockHub();
        Hub endHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(VALID_DURATION)
                .distance(null)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이동 거리는 필수 입니다.");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.1, -10.5})
    @DisplayName("실패: 이동 거리가 0 이하일 경우 IllegalArgumentException 발생")
    void createHubRoute_Fail_InvalidDistance(double invalidDistance) {
        // given
        Hub startHub = createMockHub();
        Hub endHub = createMockHub();

        // when & then
        assertThatThrownBy(() -> HubRoute.builder()
                .startHub(startHub)
                .endHub(endHub)
                .duration(VALID_DURATION)
                .distance(invalidDistance)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 거리는 0보다 커야 합니다.");
    }

}