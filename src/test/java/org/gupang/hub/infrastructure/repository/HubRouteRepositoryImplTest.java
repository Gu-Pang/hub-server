package org.gupang.hub.infrastructure.repository;

import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.entity.HubRoute;
import org.gupang.hub.fixture.AddressFixture;
import org.gupang.hub.fixture.CoordinateFixture;
import org.gupang.hub.fixture.HubFixture;
import org.gupang.hub.infrastructure.repository.base.RepositoryTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(HubRouteRepositoryImpl.class)
class HubRouteRepositoryImplTest extends RepositoryTestBase {

    @Autowired
    private HubRouteRepositoryImpl hubRouteRepository;

    @Test
    @DisplayName("성공: Fetch Join을 통해 허브 정보가 포함된 전체 경로 목록을 조회한다")
    void findAllWithHubs_Success() {
        // given
        saveDefaultHubsAndRoute();

        // when
        List<HubRoute> result = hubRouteRepository.findAllWithHubs();

        // then
        assertThat(result).hasSize(1);
        HubRoute foundRoute = result.get(0);

        assertThat(foundRoute.getStartHub().getHubName()).isEqualTo("서울특별시 센터");
        assertThat(foundRoute.getEndHub().getHubName()).isEqualTo("부산광역시 센터");
    }

    @Test
    @DisplayName("성공: 데이터가 하나도 없을 때 전체 조회를 하면 빈 리스트를 반환한다")
    void findAllWithHubs_Empty() {
        // when
        List<HubRoute> result = hubRouteRepository.findAllWithHubs();

        // then
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    }

    private void saveDefaultHubsAndRoute() {
        Hub seoulHub = HubFixture.seoulHub();
        Hub busanHub = HubFixture.busanHub();

        Hub daejeonHub = Hub.create(
                "대전광역시 센터",
                AddressFixture.create("대전 서구 둔산로 100", null),
                CoordinateFixture.create(36.350, 127.384)
        );

        em.persist(seoulHub);
        em.persist(busanHub);
        em.persist(daejeonHub);

        HubRoute route = HubRoute.create(seoulHub, busanHub, 300, 400);

        em.persist(route);

        flushAndClear();
    }
}
