package com.golapadeok.fluo.domain.invitation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CursorPageRequest {

    @Min(value = 0, message = "최솟값은 0입니다.")
    @Schema(description = "현재 커서 아이디(마지막 아이템의 아이디)", example = "0", defaultValue = "0")
    private Integer cursorId;

    @Range(min = 5, max = 10, message = "한 페이지에 표시 될 아이템은 5이상 10이하 입니다.")
    @Schema(description = "한 페이지에 표시될 아이템의 개수", example = "5", defaultValue = "5")
    private Integer limit;

    public CursorPageRequest(Integer cursorId, Integer limit) {
        this.cursorId = cursorId;
        this.limit = limit;
    }
}
