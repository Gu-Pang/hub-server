package org.gupang.hub.presentation.dto;

import org.gupang.hub.application.hub.dto.HubInfo;

import java.util.UUID;

public record HubResponse(
        UUID hubId,
        String hubName,
        String address,
        String addressDetail
) {

    public static HubResponse from(HubInfo info) {
        return new HubResponse(
                info.hubId(),
                info.hubName(),
                info.address(),
                info.addressDetail()
        );
    }
}
