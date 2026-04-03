package org.gupang.hub.infrastructure.repository;

import org.gupang.hub.application.route.dto.RouteInfo;
import org.gupang.hub.domain.entity.HubRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface HubRouteJpaRepository extends JpaRepository<HubRoute, UUID> {

    @Query("""
        SELECT new org.gupang.hub.application.route.dto.RouteInfo(
            startHub.hubId,
            startHub.hubName,
            endHub.hubId,
            endHub.hubName,
            route.distance,
            route.duration
        )
        FROM HubRoute route
        JOIN route.startHub startHub
        JOIN route.endHub endHub
        WHERE startHub.hubId = :hubId
    """)
    List<RouteInfo> findRoutesWithHubNamesByHubId(@Param("hubId")UUID hubId);

}
