package org.gupang.hub.presentation;

import lombok.RequiredArgsConstructor;
import org.gupang.hub.application.route.dto.HubRouteInfo;
import org.gupang.hub.application.route.service.HubRouteSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/hubs")
@RestController
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteSearchService hubRouteSearchService;

    @GetMapping("/routes")
    public ResponseEntity<List<HubRouteInfo>> searchHubRoutes(
            @RequestParam UUID startHubId,
            @RequestParam UUID endHubId
    ) {
        List<HubRouteInfo> hubRouteInfos = hubRouteSearchService.searchRoute(startHubId, endHubId);

        return ResponseEntity.ok().body(hubRouteInfos);
    }
}
