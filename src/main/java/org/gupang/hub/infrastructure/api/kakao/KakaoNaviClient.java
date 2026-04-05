package org.gupang.hub.infrastructure.api.kakao;

import org.gupang.hub.infrastructure.api.kakao.dto.KakaoNaviResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoNaviClient", url = "https://apis-navi.kakaomobility.com")
public interface KakaoNaviClient {

    @GetMapping("/v1/directions")
    KakaoNaviResponse getDirections(
            @RequestHeader("Authorization") String apiKey,
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination
    );

}
