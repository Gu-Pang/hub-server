package org.gupang.hub.infrastructure.route;

import org.assertj.core.groups.Tuple;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.application.route.dto.HubRouteInfo;
import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.entity.HubRoute;
import org.gupang.hub.domain.repository.HubRouteRepository;
import org.gupang.hub.domain.route.PathType;
import org.gupang.hub.global.exception.HubErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JGraphTPathFinderTest {

    @Mock
    private HubRouteRepository hubRouteRepository;

    @InjectMocks
    private JGraphTPathFinder jGraphTPathFinder;

    private UUID hubA;
    private UUID hubB;
    private UUID hubC;
    private UUID hubD;

    @BeforeEach
    void setUp() {
        hubA = UUID.randomUUID();
        hubB = UUID.randomUUID();
        hubC = UUID.randomUUID();
        hubD = UUID.randomUUID();
    }

    @Test
    @DisplayName("PathType이 JGRAPHT일 경우 true를 반환")
    void supports() {
        boolean result = jGraphTPathFinder.supports(PathType.JGRAPHT);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("성공: 우회 경로가 직통 경로보다 빠를 경우, 우회 경로로 반환")
    void findPath_Success_ShortestPath() {
        // given
        HubRoute routeAB = createMockRoute(hubA, hubB, 10);
        HubRoute routeBC = createMockRoute(hubB, hubC, 20);
        HubRoute routeAC = createMockRoute(hubA, hubC, 50);

        List<HubRoute> allRoutes = List.of(routeAB, routeBC, routeAC);
        given(hubRouteRepository.findAllWithHubs()).willReturn(allRoutes);

        // when
        List<HubRouteInfo> result = jGraphTPathFinder.findPath(hubA, hubC);

        // then
        assertThat(result).hasSize(2)
                .extracting("startHubId", "endHubId", "duration")
                .containsExactly(
                        Tuple.tuple((hubA), hubB, 10),
                        Tuple.tuple(hubB, hubC, 20));
    }

    @Test
    @DisplayName("실패: 출발지와 도착지 간의 연결된 경로가 없으면 예외가 발생")
    void findPath_Fail_DisconnectedGraph() {
        // given
        HubRoute routeAB = createMockRoute(hubA, hubB, 10);
        HubRoute routeCD = createMockRoute(hubC, hubD, 15);

        List<HubRoute> allRoutes = List.of(routeAB, routeCD);
        given(hubRouteRepository.findAllWithHubs()).willReturn(allRoutes);

        // when & then
        assertThatThrownBy(() -> jGraphTPathFinder.findPath(hubA, hubC))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(HubErrorCode.HUB_ROUTE_NOT_FOUND.getMessage());
    }


    private HubRoute createMockRoute(UUID startHubId, UUID endHubId, int duration) {
        HubRoute hubRoute = mock(HubRoute.class);
        Hub startHub = mock(Hub.class);
        Hub endHub = mock(Hub.class);

        given(startHub.getHubId()).willReturn(startHubId);
        given(endHub.getHubId()).willReturn(endHubId);

        given(hubRoute.getStartHub()).willReturn(startHub);
        given(hubRoute.getEndHub()).willReturn(endHub);
        given(hubRoute.getDuration()).willReturn(duration);

        return hubRoute;
    }

}