package org.gupang.hub.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    @Test
    @DisplayName("성공: 기본, 상세 주소가 있는 경우 Address 생성")
    void createAddress_Success() {
        // given
        String baseAddress = "서울특별시 강남구 테헤란로 123";
        String detailAddress = "한국 타이어 빌딩 2층";

        // when
        Address address = new Address(baseAddress, detailAddress);

        // then
        assertThat(address.getAddress()).isEqualTo(baseAddress);
        assertThat(address.getAddressDetail()).isEqualTo(detailAddress);
    }

    @Test
    @DisplayName("성공: 기본 주소는 있고 상세 주소가 없는 경우 Address 생성")
    void createAddress_Success_WithoutDetailAddress() {
        // given
        String baseAddress = "서울특별시 강남구 테헤란로 123";
        String detailAddress = null;

        // when
        Address address = new Address(baseAddress, detailAddress);

        // then
        assertThat(address.getAddress()).isEqualTo(baseAddress);
        assertThat(address.getAddressDetail()).isEqualTo(detailAddress);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("실패: 기본 주소가 비어 있거나 공백인 경우 IllegalArgumentException 발생")
    void createAddress_Fail_InvalidAddress(String invalidAddress) {
        // when & then
        assertThatThrownBy(() -> new Address(invalidAddress, "상세 주소"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기본 주소는 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 기본 주소가 null일 경우 IllegalArgumentException 발생")
    void createAddress_Fail_AddressIsNull() {
        // when & then
        assertThatThrownBy(() -> new Address(null, "상세 주소"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기본 주소는 필수 입니다.");
    }

}