package com.golapadeok.fluo.domain.tag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagCreateRequest {
    @NotNull(message = "워크스페이스 아이디는 필수값 입니다.")
    @Schema(description = "태그가 포함된 워크스페이스 아이디", example = "1")
    private Integer workspaceId;
    @NotEmpty(message = "태그 이름은 필수값 입니다.")
    @Schema(description = "태그 이름", example = "tagName")
    private String name;
    @NotEmpty(message = "색상 코드는 필수값 입니다.")
    @Schema(description = "색상 코드", example = "######")
    private String colorCode;
}
