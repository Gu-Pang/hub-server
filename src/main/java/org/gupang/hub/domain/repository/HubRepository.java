package org.gupang.hub.domain.repository;

import org.gupang.hub.domain.entity.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Page<Hub> findAll(Pageable pageable);

    Optional<Hub> findById(UUID hubId);

    Hub save(Hub hub);

    boolean existsByHubName(String hubName);

}
