package com.golapadeok.fluo.domain.workspace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberDeleteRequest {
    @NotNull(message = "삭제할 회원 아이디는 필수값입니다.")
    @Schema(description = "삭제할 회원 아이디", example = "1")
    private Integer memberId;

}
