package org.gupang.hub.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gupang.common.entity.BaseEntity;
import org.gupang.hub.domain.vo.Address;
import org.gupang.hub.domain.vo.Coordinate;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "p_hub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hubId;

    @Column(nullable = false)
    private String hubName;

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    @Builder
    public Hub(String hubName, Address address, Coordinate coordinate) {
        validateHubInfo(hubName, address, coordinate);

        this.hubName = hubName;
        this.address = address;
        this.coordinate = coordinate;
    }

    private void validateHubInfo(String hubName, Address address, Coordinate coordinate) {
        if (hubName == null || hubName.isBlank()) {
            throw new IllegalArgumentException("허브명은 필수 입니다.");
        }

        Objects.requireNonNull(address, "허브 주소는 필수 입니다.");
        Objects.requireNonNull(coordinate, "허브의 좌표는 필수 입니다.");
    }
}
