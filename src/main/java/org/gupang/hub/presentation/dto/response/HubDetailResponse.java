package org.gupang.hub.presentation.dto.response;

import org.gupang.hub.application.hub.dto.HubDetailInfo;

import java.util.UUID;

public record HubDetailResponse(
        UUID hubId,
        String hubName,
        String address,
        String addressDetail,
        double latitude,
        double longitude
) {

    public static HubDetailResponse from(HubDetailInfo info) {
        return new HubDetailResponse(
                info.hubId(),
                info.hubName(),
                info.address(),
                info.addressDetail(),
                info.latitude(),
                info.longitude()
        );
    }
}
