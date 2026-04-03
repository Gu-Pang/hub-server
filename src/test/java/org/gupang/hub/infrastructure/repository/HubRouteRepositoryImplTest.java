package org.gupang.hub.infrastructure.repository;

import jakarta.persistence.EntityManager;
import org.gupang.hub.application.route.dto.HubRouteInfo;
import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.entity.HubRoute;
import org.gupang.hub.domain.vo.Address;
import org.gupang.hub.domain.vo.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(HubRouteRepositoryImpl.class)
@TestPropertySource(properties = {
        "spring.sql.init.mode=never",
        "spring.jpa.hibernate.ddl-auto=create"
})
class HubRouteRepositoryImplTest {

    @Autowired
    private HubRouteRepositoryImpl hubRouteRepository;

    @Autowired
    private EntityManager em;

    private Hub seoulHub;
    private Hub busanHub;
    private Hub daejeonHub;

    @BeforeEach
    void SetUp() {
        seoulHub = Hub.builder()
                .hubName("서울특별시 센터")
                .address(new Address("서울특별시 송파구 송파대로 55", "물류동 1층"))
                .coordinate(new Coordinate(37.478, 127.123))
                .build();

        busanHub = Hub.builder()
                .hubName("부산광역시 센터")
                .address(new Address("부산 동구 중앙대로 206", "항만연계센터"))
                .coordinate(new Coordinate(35.115, 127.350))
                .build();

        daejeonHub = Hub.builder()
                .hubName("대전광역시 센터")
                .address(new Address("대전 서구 둔산로 100", null))
                .coordinate(new Coordinate(36.350, 127.384))
                .build();

        em.persist(seoulHub);
        em.persist(busanHub);
        em.persist(daejeonHub);

        em.flush();

        HubRoute route = HubRoute.builder()
                .startHub(seoulHub)
                .endHub(busanHub)
                .duration(300)
                .distance(400.5)
                .build();

        em.persist(route);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("성공: 조회하려는 허브가 출발지일 경우")
    void findRoutes_WhenHubIsStartHub() {
        // when
        List<HubRouteInfo> results = hubRouteRepository.findRoutesWithHubNamesByHubId(seoulHub.getHubId());

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).startHubName()).isEqualTo(seoulHub.getHubName());
        assertThat(results.get(0).endHubName()).isEqualTo(busanHub.getHubName());
    }

    @Test
    @DisplayName("성공: 직접 연결된 경로가 없을 경우 빈 리스트 반환")
    void findRoutes_WhenNoDirectRouteExists() {
        // when
        List<HubRouteInfo> results = hubRouteRepository.findRoutesWithHubNamesByHubId(daejeonHub.getHubId());

        // then
        assertThat(results).isEmpty();
    }

}