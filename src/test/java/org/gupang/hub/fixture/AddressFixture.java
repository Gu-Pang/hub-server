package org.gupang.hub.fixture;

import org.gupang.hub.domain.vo.Address;

public class AddressFixture {

    public static Address create() {
        return seoul();
    }

    public static Address seoul() {
        return new Address("서울특별시 송파구 송파대로 55", "물류동 1층");
    }

    public static Address busan() {
        return new Address("부산 동구 중앙대로 206", "항만연계센터");
    }

    public static Address create(String cityAddress, String detailAddress) {
        return new Address(cityAddress, detailAddress);
    }
}
