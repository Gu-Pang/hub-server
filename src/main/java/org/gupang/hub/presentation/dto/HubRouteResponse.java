package org.gupang.hub.presentation.dto;

import org.gupang.hub.application.route.dto.HubRouteInfo;

import java.util.UUID;

public record HubRouteResponse(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        int estimatedDistance,
        int estimatedDuration
) {

    public static HubRouteResponse from(HubRouteInfo info) {
        return new HubRouteResponse(
                info.startHubId(),
                info.startHubName(),
                info.endHubId(),
                info.endHubName(),
                info.estimatedDistance(),
                info.estimatedDuration()
        );
    }
}
