package org.gupang.hub.application.hub.service;

import org.gupang.common.exception.CustomException;
import org.gupang.hub.application.hub.dto.HubDetailInfo;
import org.gupang.hub.application.hub.dto.HubInfo;
import org.gupang.hub.domain.entity.Hub;
import org.gupang.hub.domain.repository.HubRepository;
import org.gupang.hub.domain.vo.Address;
import org.gupang.hub.domain.vo.Coordinate;
import org.gupang.hub.global.exception.HubErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HubServiceTest {

    @Mock
    private HubRepository hubRepository;

    @InjectMocks
    private HubService hubService;

    private Hub dummyHub;
    private UUID dummyHubId;

    private Address dummyAddress;
    private Coordinate dummyCoordinate;

    private final String HUB_NAME = "서울특별시 센터";

    @BeforeEach
    void setUp() {
        dummyHubId = UUID.randomUUID();
        dummyAddress = new Address("서울특별시 송파구 송파대로 55", "물류동 1층");
        dummyCoordinate = new Coordinate(37.478, 127.123);

        dummyHub = mock(Hub.class);
    }

    @Test
    @DisplayName("성공: 전체 허브 조회 시 Page<HubInfo>를 반환")
    void findAllHubs_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        given(dummyHub.getHubName()).willReturn(HUB_NAME);
        given(dummyHub.getAddress()).willReturn(dummyAddress);

        Page<Hub> hubPage = new PageImpl<>(List.of(dummyHub), pageable, 1);
        given(hubRepository.findAll(pageable)).willReturn(hubPage);

        // when
        Page<HubInfo> result = hubService.findAllHubs(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).hubName()).isEqualTo(HUB_NAME);
        assertThat(result.getContent().get(0).address()).isEqualTo(dummyAddress.getAddress());
        assertThat(result.getContent().get(0).addressDetail()).isEqualTo(dummyAddress.getAddressDetail());
    }

    @Test
    @DisplayName("성공: 등록된 허브가 없을 경우 빈 Page 반환")
    void findAllHubs_Empty() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Hub> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        given(hubRepository.findAll(pageable)).willReturn(emptyPage);

        // when
        Page<HubInfo> result = hubService.findAllHubs(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("성공: 존재하는 hubId로 상세 조회 시 HubInfo 반환")
    void findHubById_Success() {
        // given
        given(dummyHub.getHubId()).willReturn(dummyHubId);
        given(dummyHub.getHubName()).willReturn(HUB_NAME);
        given(dummyHub.getAddress()).willReturn(dummyAddress);
        given(dummyHub.getCoordinate()).willReturn(dummyCoordinate);

        given(hubRepository.findById(dummyHubId)).willReturn(Optional.of(dummyHub));

        // when
        HubDetailInfo result = hubService.findHubById(dummyHubId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.hubId()).isEqualTo(dummyHubId);
        assertThat(result.hubName()).isEqualTo(HUB_NAME);
        assertThat(result.address()).isEqualTo(dummyAddress.getAddress());
        assertThat(result.addressDetail()).isEqualTo(dummyAddress.getAddressDetail());
        assertThat(result.latitude()).isEqualTo(dummyCoordinate.getLatitude());
        assertThat(result.longitude()).isEqualTo(dummyCoordinate.getLongitude());
    }

    @Test
    @DisplayName("실패: 존재하지 않는 hubId로 상세 조회 시 에러 발생")
    void findHubById_Fail_NotFound() {
        // given
        UUID invalidId = UUID.randomUUID();
        given(hubRepository.findById(invalidId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> hubService.findHubById(invalidId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(HubErrorCode.HUB_NOT_FOUND.getMessage());
    }
}
