package org.gupang.hub.application.route.dto;

import org.gupang.hub.domain.entity.HubRoute;

import java.util.UUID;

public record HubRouteInfo(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        int estimatedDistance,
        int estimatedDuration
) {

    public static HubRouteInfo from(HubRoute hubRoute) {
        return new HubRouteInfo(
                hubRoute.getStartHub().getHubId(),
                hubRoute.getStartHub().getHubName(),
                hubRoute.getEndHub().getHubId(),
                hubRoute.getEndHub().getHubName(),
                hubRoute.getDistance(),
                hubRoute.getDuration()
        );
    }
    
}
