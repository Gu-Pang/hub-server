package org.gupang.hub.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address;
    private String addressDetail;

    public Address(String address, String addressDetail) {
        validateAddress(address);

        this.address = address;
        this.addressDetail = addressDetail;
    }

    private void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("기본 주소는 필수 입니다.");
        }
    }

}
