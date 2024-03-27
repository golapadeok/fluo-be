package com.golapadeok.fluo.domain.workspace.dto;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortType {
    CREATE("create_date"),
    END("end_date"),
    PRIORITY("priority");

    private final String sortName;

    SortType(String sortName) {
        this.sortName = sortName;
    }

    public Sort getSort(Sort.Direction ascending) {
        return Sort.by(ascending, sortName);
    }

}
