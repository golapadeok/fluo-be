package com.golapadeok.fluo.domain.workspace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO KDY 추후 offset -> cursorId로 변경해야함.
public class WorkspacePageRequest {
    @Range(min = 10, max = 50, message = "한 페이지에 표시될 아이템는 10이상 50이하 입니다.")
    @Schema(description = "한 페이지에 표시될 아이템 개수", example = "10")
    private Integer limit;

    @Schema(description = "현재 페이지 -> 0 부터 시작", example = "0")
    private Integer offset;

    private Boolean ascending;

    public WorkspacePageRequest(Integer limit, Integer offset, Boolean ascending) {
        this.limit = limit == null ? 10 : limit;
        this.offset = offset == null || offset < 0 ? 0 : offset;
        this.ascending = ascending != null && ascending;
    }
}
