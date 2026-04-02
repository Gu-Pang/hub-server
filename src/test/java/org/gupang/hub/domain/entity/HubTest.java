package org.gupang.hub.domain.entity;

import org.gupang.hub.domain.vo.Address;
import org.gupang.hub.domain.vo.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HubTest {

    private final String validHubName = "서울 허브";
    private final Address validAddress = new Address("서울시 강남구 테헤란로 123", "한국 타이어 빌딩 2층");
    private final Coordinate validCoordinate = new Coordinate(37.514575, 127.028361548);

    @Test
    @DisplayName("성공: 정상적인 정보가 입력될 경우 Hub 생성")
    void createHub_Success() {
        // when
        Hub hub = Hub.builder()
                .hubName(validHubName)
                .address(validAddress)
                .coordinate(validCoordinate)
                .build();

        // then
        assertThat(hub.getHubName()).isEqualTo(validHubName);
        assertThat(hub.getAddress()).isEqualTo(validAddress);
        assertThat(hub.getCoordinate()).isEqualTo(validCoordinate);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("실패: 허브명이 비어있거나 공백일 경우 IllegalArgumentException 발생")
    void createHub_Fail_InvalidHubName(String invalidHubName) {
        // when & then
        assertThatThrownBy(() -> Hub.builder()
                .hubName(invalidHubName)
                .address(validAddress)
                .coordinate(validCoordinate)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브명은 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 허브명이 null일 경우 IllegalArgumentException 발생")
    void createHub_Fail_HubNameIsNull() {
        // when & then
        assertThatThrownBy(() -> Hub.builder()
                .hubName(null)
                .address(validAddress)
                .coordinate(validCoordinate)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브명은 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 주소 정보가 null일 경우 NullPointerException 발생")
    void createHub_Fail_AddressIsNull() {
        // when & then
        assertThatThrownBy(() -> Hub.builder()
                .hubName(validHubName)
                .address(null)
                .coordinate(validCoordinate)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("허브 주소는 필수 입니다.");
    }

    @Test
    @DisplayName("실패: 좌표 정보가 null일 경우 NullPointerException 발생")
    void createHub_Fail_CoordinateIsNull() {
        // when & then
        assertThatThrownBy(() -> Hub.builder()
                .hubName(validHubName)
                .address(validAddress)
                .coordinate(null)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("허브의 좌표는 필수 입니다.");
    }


}