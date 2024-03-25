package com.golapadeok.fluo.domain.state.dto.request;

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
    private Integer workspaceId;

    @NotNull(message = "상태 이름은 필수값 입니다.")
    private String name;
}
