package com.golapadeok.fluo.domain.task.dto.request;

import com.golapadeok.fluo.domain.task.domain.LabelColor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskUpdateRequest {
    @NotNull(message = "상태 아이디는 필수 입력입니다.")
    @Schema(description = "업무의 상태 아이디", example = "1")
    private Integer stateId;

    @NotEmpty(message = "업무 제목은 필수 입력입니다.")
    @Schema(description = "업무 제목", example = "title")
    private String title;

    @Schema(description = "업무 설명", example = "description")
    private String description;

    @NotNull(message = "태그 아이디는 필수 입력입니다.")
    @Schema(description = "업무에 설정할 태그 아이디", example = "1")
    private Integer tag;

    @NotNull(message = "관리자는 최소 한명 이상입니다.")
    @Schema(description = "업무 관리자 아이디 리스트", example = "[1, 2, 3]")
    private List<Integer> managers;

    @NotNull(message = "공개 여부는 필수값 입니다.")
    @Schema(description = "업무 공개 여부")
    private Boolean isPrivate;

    @NotNull(message = "중요도는 필수 입력값 입니다.")
    @Range(min = 1, max = 10, message = "중요도는 1이상 10이하 입니다.")
    @Schema(description = "업무 중요도", example = "5")
    private Integer priority;

    @NotNull(message = "라벨 색상은 필수 입력값 입니다.")
    @Schema(description = "라벨 색상", example = "RED")
    private LabelColor labelColor;

    @NotNull(message = "날짜는 필수 입력입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "업무 시작 날짜", example = "2020-01-01")
    private LocalDate startDate;

    @NotNull(message = "날짜는 필수 입력입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "업무 종료 날짜", example = "2030-01-01")
    private LocalDate endDate;

    public TaskUpdateRequest(Integer stateId, String title, String description, Integer tag, List<Integer> managers, Boolean isPrivate, Integer priority, LabelColor labelColor, LocalDate startDate, LocalDate endDate) {
        this.stateId = stateId;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.managers = managers;
        this.isPrivate = isPrivate;
        this.priority = priority;
        this.labelColor = LabelColor.from(labelColor.toString());
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
