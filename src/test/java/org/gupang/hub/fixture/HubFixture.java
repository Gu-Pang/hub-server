package org.gupang.hub.fixture;

import org.gupang.hub.domain.entity.Hub;

public class HubFixture {

    public static Hub create(String name) {
        return Hub.create(
                name,
                AddressFixture.create(),
                CoordinateFixture.create()
        );
    }

    public static Hub seoulHub() {
        return Hub.create(
                "서울특별시 센터",
                AddressFixture.seoul(),
                CoordinateFixture.seoul()
        );
    }

    public static Hub busanHub() {
        return Hub.create(
                "부산광역시 센터",
                AddressFixture.busan(),
                CoordinateFixture.busan()
        );
    }
}
