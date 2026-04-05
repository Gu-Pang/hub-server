package org.gupang.hub.presentation;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.application.hub.dto.HubDetailInfo;
import org.gupang.hub.application.hub.dto.HubInfo;
import org.gupang.hub.application.hub.service.HubService;
import org.gupang.hub.presentation.dto.response.HubDetailResponse;
import org.gupang.hub.presentation.dto.response.HubResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/hubs")
@RestController
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @GetMapping
    public ResponseEntity<Page<HubResponse>> searchAllHubs(Pageable pageable) {
        Page<HubInfo> hubInfos = hubService.findAllHubs(pageable);
        Page<HubResponse> hubResponses = hubInfos.map(HubResponse::from);

        return ResponseEntity.ok(hubResponses);
    }

    @GetMapping("/{HubId}")
    public ResponseEntity<HubDetailResponse> searchHub(@PathVariable UUID hubId) {
        HubDetailInfo hubDetailInfo = hubService.findHubById(hubId);

        return ResponseEntity.ok(HubDetailResponse.from(hubDetailInfo));
    }
}
