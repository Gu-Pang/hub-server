package org.gupang.hub.domain.vo;

import org.gupang.common.exception.CustomException;
import org.gupang.hub.global.exception.HubErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoordinateTest {

    private static final double VALID_LATITUDE = 37.514575;
    private static final double VALID_LONGITUDE = 127.028361548;

    @Test
    @DisplayName("성공: 한국 범위 내의 위도와 경도가 주어질 경우 Coordinate 객체가 생성 된다.")
    void createCoordinate_Success() {
        // when
        Coordinate coordinate = new Coordinate(VALID_LATITUDE, VALID_LONGITUDE);

        // then
        assertThat(coordinate.getLatitude()).isEqualTo(VALID_LATITUDE);
        assertThat(coordinate.getLongitude()).isEqualTo(VALID_LONGITUDE);
    }

    @ParameterizedTest
    @ValueSource(doubles = {32.9, 39.0, 0.0, 90.0})
    @DisplayName("실패: 한국 범위를 벗어난 위도가 입력될 경우 CustomException 발생")
    void createCoordinate_Fail_InvalidLatitude(double invalidLatitude) {
        // when & then
        assertThatThrownBy(() -> new Coordinate(invalidLatitude, VALID_LONGITUDE))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(HubErrorCode.INVALID_LATITUDE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {124.4, 132.1, 0.0, 180.0})
    @DisplayName("실패: 한국 범위를 벗어난 경도가 입력될 경우 CustomException 발생")
    void createCoordinate_Fail_InvalidLongitude(double invalidLongitude) {
        // when & then
        assertThatThrownBy(() -> new Coordinate(VALID_LATITUDE, invalidLongitude))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(HubErrorCode.INVALID_LONGITUDE.getMessage());
    }

}