package org.gupang.hub.domain.repository;

import org.gupang.hub.domain.entity.HubRoute;

import java.util.List;

public interface HubRouteRepository {

    List<HubRoute> findAllWithHubs();

}
