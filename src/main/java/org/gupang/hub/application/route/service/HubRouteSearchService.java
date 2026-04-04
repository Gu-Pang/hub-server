package org.gupang.hub.application.route.service;

import lombok.RequiredArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.application.route.dto.HubRouteInfo;
import org.gupang.hub.domain.route.PathFinder;
import org.gupang.hub.domain.route.PathType;
import org.gupang.hub.global.exception.HubErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteSearchService {

    private final List<PathFinder> pathFinders;

    public List<HubRouteInfo> searchRoute(UUID startHubId, UUID endHubId) {
        validateHubIds(startHubId, endHubId);
        PathFinder pathFinder = selectPathFinder();

        return pathFinder.findPath(startHubId, endHubId);
    }

    private void validateHubIds(UUID startHubId, UUID endHubId) {
        if (startHubId.equals(endHubId)) {
            throw new CustomException(HubErrorCode.INVALID_HUB_ROUTE);
        }
    }

    private PathFinder selectPathFinder() {
        PathType targetType = PathType.JGRAPHT;

        return pathFinders.stream()
                .filter(pf -> pf.supports(targetType))
                .findFirst()
                .orElseThrow(() -> new CustomException(HubErrorCode.HUB_ROUTE_NOT_FOUND));
    }
}
