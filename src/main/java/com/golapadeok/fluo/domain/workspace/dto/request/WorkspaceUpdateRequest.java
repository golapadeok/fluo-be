package com.golapadeok.fluo.domain.workspace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkspaceUpdateRequest {
    @NotEmpty(message = "워크스페이스 제목은 필수값 입니다.")
    @Schema(description = "워크스페이스 제목", example = "title")
    private String title;
}
