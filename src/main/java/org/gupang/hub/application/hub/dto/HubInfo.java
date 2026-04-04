package org.gupang.hub.application.hub.dto;

import org.gupang.hub.domain.entity.Hub;

import java.util.UUID;

public record HubInfo(
        UUID hubId,
        String hubName,
        String address,
        String addressDetail
) {

    public static HubInfo from(Hub hub) {
        return new HubInfo(
                hub.getHubId(),
                hub.getHubName(),
                hub.getAddress().getAddress(),
                hub.getAddress().getAddressDetail()
        );
    }
}
