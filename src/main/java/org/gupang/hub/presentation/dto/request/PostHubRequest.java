package org.gupang.hub.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.gupang.hub.application.hub.dto.CreateHubCommand;

public record PostHubRequest(
        @NotBlank(message = "허브 이름은 필수입니다.")
        String name,

        @NotBlank(message = "허브 주소는 필수입니다.")
        String address,

        String addressDetail,

        @NotNull(message = "위도 정보는 필수입니다.")
        Double latitude,

        @NotNull(message = "경도 정보는 필수입니다.")
        Double longitude
) {

        public CreateHubCommand toCommand() {
                return new CreateHubCommand(
                        this.name,
                        this.address,
                        this.addressDetail,
                        this.latitude,
                        this.longitude);
        }
}
