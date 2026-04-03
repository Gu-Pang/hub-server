package org.gupang.hub.domain.repository;

import org.gupang.hub.application.route.dto.RouteInfo;

import java.util.List;
import java.util.UUID;

public interface HubRouteRepository {

    List<RouteInfo> findRoutesWithHubNamesByHubId(UUID hubId);

}
