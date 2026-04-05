package org.gupang.hub.domain.route;

public record RouteEstimate(
        Integer estimateDuration,
        Integer estimateDistance
) {
    private static final String INVALID_DISTANCE_ERROR_MESSAGE = "예상 이동 거리는 0보다 커야 합니다.";
    private static final String INVALID_DURATION_ERROR_MESSAGE = "예상 소요 시간은 0보다 커야 합니다.";

    public RouteEstimate {
        if (estimateDistance <= 0) {
            throw new IllegalArgumentException(INVALID_DISTANCE_ERROR_MESSAGE);
        }

        if (estimateDuration <= 0) {
            throw new IllegalArgumentException(INVALID_DURATION_ERROR_MESSAGE);
        }
    }
}
