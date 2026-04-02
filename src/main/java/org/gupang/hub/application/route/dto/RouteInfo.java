package org.gupang.hub.application.route.dto;

import java.util.UUID;

public record RouteInfo(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        double estimatedDistance,
        int estimatedDuration
) {

}
