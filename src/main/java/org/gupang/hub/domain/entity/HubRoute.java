package org.gupang.hub.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gupang.common.entity.BaseEntity;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.global.exception.HubErrorCode;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "p_hub_route", uniqueConstraints = {
        @UniqueConstraint(name = "uk_hub_route", columnNames = {"start_hub_id", "end_hub_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class HubRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_hub_id", nullable = false)
    private Hub startHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_hub_id", nullable = false)
    private Hub endHub;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer distance;

    @Builder
    public HubRoute(Hub startHub, Hub endHub, Integer duration, Integer distance) {
        validateHubs(startHub, endHub);
        validateDurationAndDistance(duration, distance);

        this.startHub = startHub;
        this.endHub = endHub;
        this.duration = duration;
        this.distance = distance;
    }

    private void validateHubs(Hub startHub, Hub endHub) {
        Objects.requireNonNull(startHub, "출발 허브는 필수 입니다.");
        Objects.requireNonNull(endHub, "도착 허브는 필수 입니다.");

        if (startHub.getHubId().equals(endHub.getHubId())) {
            throw new CustomException(HubErrorCode.INVALID_HUB_ROUTE);
        }
    }

    private void validateDurationAndDistance(Integer duration, Integer distance) {
        Objects.requireNonNull(duration, "소요 시간은 필수 입니다.");
        Objects.requireNonNull(distance, "이동 거리는 필수 입니다.");

        if (duration <= 0) {
            throw new IllegalArgumentException("소요 시간은 0보다 커야 합니다.");
        }

        if (distance <= 0) {
            throw new IllegalArgumentException("이동 거리는 0보다 커야 합니다.");
        }
    }
}
