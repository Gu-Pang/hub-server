package org.gupang.hub.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.domain.exception.HubErrorCode;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {

    private static final double MIN_LATITUDE = 33.0;
    private static final double MAX_LATITUDE = 38.9;
    private static final double MIN_LONGITUDE = 124.5;
    private static final double MAX_LONGITUDE = 132.0;

    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new CustomException(HubErrorCode.INVALID_LATITUDE);
        }

        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new CustomException(HubErrorCode.INVALID_LONGITUDE);
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

}
