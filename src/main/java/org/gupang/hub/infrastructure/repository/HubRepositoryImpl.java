package org.gupang.hub.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.repository.HubRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final HubJpaRepository hubJpaRepository;

    @Override
    public Page<Hub> findAll(Pageable pageable) {
        return hubJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Hub> findById(UUID hubId) {
        return hubJpaRepository.findById(hubId);
    }

    @Override
    public Hub save(Hub hub) {
        return hubJpaRepository.save(hub);
    }

    @Override
    public boolean existsByHubName(String hubName) {
        return hubJpaRepository.existsByHubName(hubName);
    }
}
