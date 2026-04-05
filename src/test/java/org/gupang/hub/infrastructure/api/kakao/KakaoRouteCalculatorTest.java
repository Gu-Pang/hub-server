package org.gupang.hub.infrastructure.api.kakao;

import org.gupang.hub.domain.vo.Coordinate;
import org.gupang.hub.infrastructure.api.kakao.dto.KakaoNaviResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KakaoRouteCalculatorTest {

    @Mock
    private KakaoNaviClient kakaoNaviClient;

    @InjectMocks
    private KakaoRouteCalculator kakaoRouteCalculator;

    private static final Coordinate START_COORDINATE = new Coordinate(37.3947, 127.1111);
    private static final Coordinate END_COORDINATE = new Coordinate(37.4020, 127.1086);
    private static final Integer ERROR_RESULT_CODE = 1;

    @Test
    @DisplayName("실패: 검색된 경로가 없는 경우 IllegalStateException 발생")
    void calculate_NoRoutes_ThrowsException() {
        KakaoNaviResponse emptyResponse = new KakaoNaviResponse(List.of());

        given(kakaoNaviClient.getDirections(anyString(), anyString(), anyString(), anyString()))
                .willReturn(emptyResponse);

        assertThatThrownBy(() -> kakaoRouteCalculator.calculate(START_COORDINATE, END_COORDINATE))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("실패: 카카오 API가 에러 코드를 반환할 경우 IllegalStateException 발생")
    void calculate_ApiError_ThrowsException() {
        KakaoNaviResponse errorResponse = new KakaoNaviResponse(List.of(
                new KakaoNaviResponse.Route(ERROR_RESULT_CODE, null)
        ));

        given(kakaoNaviClient.getDirections(anyString(), anyString(), anyString(), anyString()))
                .willReturn(errorResponse);

        assertThatThrownBy(() -> kakaoRouteCalculator.calculate(START_COORDINATE, END_COORDINATE))
                .isInstanceOf(IllegalStateException.class);
    }
}
