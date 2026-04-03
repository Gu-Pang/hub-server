package org.gupang.hub.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.application.route.dto.RouteInfo;
import org.gupang.hub.domain.repository.HubRouteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRouteRepositoryImpl implements HubRouteRepository {

    private final HubRouteJpaRepository hubRouteJpaRepository;

    @Override
    public List<RouteInfo> findRoutesWithHubNamesByHubId(UUID hubId) {
        return hubRouteJpaRepository.findRoutesWithHubNamesByHubId(hubId);
    }

}
