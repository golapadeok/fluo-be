package com.golapadeok.fluo.domain.state.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StateCreateRequest {
    @NotNull(message = "워크스페이스 아이디는 필수값 입니다.")
    @Schema(description = "상태를 추가할 워크스페이스 아이디", example = "1")
    private Integer workspaceId;

    @NotEmpty(message = "상태 이름은 필수값 입니다.")
    @Schema(description = "상태 이름", example = "state")
    private String name;
}
