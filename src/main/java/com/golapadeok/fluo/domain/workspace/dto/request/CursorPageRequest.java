package com.golapadeok.fluo.domain.workspace.dto.request;

import com.golapadeok.fluo.domain.workspace.dto.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CursorPageRequest{
    @Range(min = 10, max = 50, message = "한 페이지에 표시될 아이템는 10이상 50이하 입니다.")
    @Schema(description = "한 페이지에 표시될 아이템 개수", example = "10", defaultValue = "10")
    private Integer limit;

    @Min(value = 0, message = "최소값은 0입니다.")
    @Schema(description = "현재 오프셋 아이디", example = "0", defaultValue = "0")
    private Integer cursorId;

    private Sort.Direction ascending;

    private SortType sortType;

    public CursorPageRequest(Integer limit, Integer cursorId, Sort.Direction ascending, String sortType) {
        this.limit = limit == null ? 10 : limit;
        this.cursorId = cursorId == null || cursorId < 0 ? 0 : cursorId;
        this.ascending = ascending == null ? Sort.Direction.DESC : ascending;
        this.sortType = sortType == null ? SortType.END : SortType.valueOf(sortType.toUpperCase());
    }
}
