package com.golapadeok.fluo.domain.invitation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "초대코드 요청 데이터")
public class InviteRequest {

    @NotEmpty(message = "초대코드는 필수 입력입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "초대코드 형식이 맞지 않습니다.")
    private String invitationCode;

}
