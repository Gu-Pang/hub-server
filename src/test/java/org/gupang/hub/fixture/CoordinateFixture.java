package org.gupang.hub.fixture;

import org.gupang.hub.domain.vo.Coordinate;

public class CoordinateFixture {

    public static Coordinate create() {
        return seoul();
    }

    public static Coordinate create(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }

    public static Coordinate seoul() {
        return new Coordinate(37.478, 127.123);
    }

    public static Coordinate busan() {
        return new Coordinate(35.115, 129.042);
    }
}
