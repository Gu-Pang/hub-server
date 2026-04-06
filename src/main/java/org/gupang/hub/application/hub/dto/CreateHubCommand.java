package org.gupang.hub.application.hub.dto;

import org.gupang.hub.domain.vo.Address;
import org.gupang.hub.domain.vo.Coordinate;

public record CreateHubCommand(
        String name,
        String address,
        String addressDetail,
        Double latitude,
        Double longitude
) {

    public Address toAddress() {
        return new Address(address, addressDetail);
    }

    public Coordinate toCoordinate() {
        return new Coordinate(latitude, longitude);
    }

}
