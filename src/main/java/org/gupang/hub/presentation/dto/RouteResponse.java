package org.gupang.hub.presentation.dto;

import org.gupang.hub.application.route.dto.RouteInfo;

import java.util.UUID;

public record RouteResponse(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        double estimatedDistance,
        int estimatedDuration
) {

    public static RouteResponse from(RouteInfo info) {
        return new RouteResponse(
                info.startHubId(),
                info.startHubName(),
                info.endHubId(),
                info.endHubName(),
                info.estimatedDistance(),
                info.estimatedDuration()
        );
    }
}
