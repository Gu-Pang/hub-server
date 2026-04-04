package org.gupang.hub.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.domain.entity.HubRoute;
import org.gupang.hub.domain.repository.HubRouteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HubRouteRepositoryImpl implements HubRouteRepository {

    private final HubRouteJpaRepository hubRouteJpaRepository;

    @Override
    public List<HubRoute> findAllWithHubs() {
        return hubRouteJpaRepository.findAllWithHubs();
    }
}
