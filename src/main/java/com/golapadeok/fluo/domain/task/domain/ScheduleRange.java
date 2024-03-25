package com.golapadeok.fluo.domain.task.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ScheduleRange {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ScheduleRange(LocalDate startDate, LocalDate endDate) {
        this(startDate.atStartOfDay(), endDate.atStartOfDay());
    }

    public ScheduleRange(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
