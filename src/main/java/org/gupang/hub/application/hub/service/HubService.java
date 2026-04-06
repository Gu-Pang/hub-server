package org.gupang.hub.application.hub.service;

import lombok.RequiredArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.application.hub.dto.CreateHubCommand;
import org.gupang.hub.application.hub.dto.HubDetailInfo;
import org.gupang.hub.application.hub.dto.HubInfo;
import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.repository.HubRepository;
import org.gupang.hub.global.exception.HubErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;

    public Page<HubInfo> findAllHubs(Pageable pageable) {
        return hubRepository.findAll(pageable)
                .map(HubInfo::from);
    }

    public HubDetailInfo findHubById(UUID hubId) {
        return hubRepository.findById(hubId)
                .map(HubDetailInfo::from)
                .orElseThrow(() -> new CustomException(HubErrorCode.HUB_NOT_FOUND));
    }

    @Transactional
    public UUID createHub(CreateHubCommand command) {
        validateUniqueHubName(command.name());
        Hub hub = Hub.create(command.name(), command.toAddress(), command.toCoordinate());

        return hubRepository.save(hub).getHubId();
    }

    private void validateUniqueHubName(String hubName) {
        if (hubRepository.existsByHubName(hubName)) {
            throw new CustomException(HubErrorCode.DUPLICATE_HUB_NAME);
        }
    }
}
