package org.gupang.hub.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gupang.hub.application.hub.service.HubService;
import org.gupang.hub.presentation.dto.request.PostHubRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HubController.class)
class HubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HubService hubService;

    @Test
    @DisplayName("성공: 유효한 요청으로 허브 생성 시 201 Created, 생성된 ID 반환")
    void createHub_Success() throws Exception {
        // given
        PostHubRequest request = new PostHubRequest("경기 허브", "경기도", "성남시", 37.3947, 127.1111);
        UUID expectedId = UUID.randomUUID();

        given(hubService.createHub(any())).willReturn(expectedId);

        // when & then
        mockMvc.perform(post("/api/v1/hubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hub_id").value(expectedId.toString()));
    }

    @Test
    @DisplayName("실패: 이름이 null이면 400 Bad Request를 반환")
    void createHub_Fail_InvalidName() throws Exception {
        // given
        PostHubRequest request = new PostHubRequest(null, "주소", "상세", 37.0, 127.0);

        // when & then
        mockMvc.perform(post("/api/v1/hubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
