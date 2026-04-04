package org.gupang.hub.application.hub.dto;

import org.gupang.hub.domain.entity.Hub;

import java.util.UUID;

public record HubDetailInfo(
        UUID hubId,
        String hubName,
        String address,
        String addressDetail,
        double latitude,
        double longitude
) {

    public static HubDetailInfo from(Hub hub) {
        return new HubDetailInfo(
                hub.getHubId(),
                hub.getHubName(),
                hub.getAddress().getAddress(),
                hub.getAddress().getAddressDetail(),
                hub.getCoordinate().getLatitude(),
                hub.getCoordinate().getLongitude()
        );
    }
}
