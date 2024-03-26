package com.golapadeok.fluo.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PagingRequest {

    @Min(value = 0, message = "최솟값은 0입니다.")
    @Schema(description = "페이지 번호", example = "0", defaultValue = "0")
    private Integer pageNum;

    @Range(min = 5, max = 10, message = "한 페이지에 표시 될 아이템은 5이상 10이하 입니다.")
    @Schema(description = "한 페이지에 표시될 아이템의 개수", example = "5", defaultValue = "5")
    private Integer limit;

    public PagingRequest(Integer pageNum, Integer limit) {
        this.pageNum = pageNum;
        this.limit = limit;
    }
}
