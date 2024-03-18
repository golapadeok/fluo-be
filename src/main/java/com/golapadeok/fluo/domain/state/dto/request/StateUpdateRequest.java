package com.golapadeok.fluo.domain.state.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StateUpdateRequest {
    @NotEmpty(message = "상태 이름은 필수값 입니다.")
    @Schema(description = "상태 이름", example = "state")
    private String name;
}
