package org.gupang.hub.domain.route;

import org.gupang.hub.application.route.dto.HubRouteInfo;

import java.util.List;
import java.util.UUID;

public interface PathFinder {

    List<HubRouteInfo> findPath(UUID startHubId, UUID endHubId);

    boolean supports(PathType pathType);

}
