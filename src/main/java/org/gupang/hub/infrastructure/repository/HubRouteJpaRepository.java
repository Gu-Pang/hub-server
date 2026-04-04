package org.gupang.hub.infrastructure.repository;

import org.gupang.hub.domain.entity.HubRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HubRouteJpaRepository extends JpaRepository<HubRoute, UUID> {

    @Query("""
                SELECT hr FROM HubRoute hr
                JOIN FETCH hr.startHub
                JOIN FETCH hr.endHub
            """)
    List<HubRoute> findAllWithHubs();

}
