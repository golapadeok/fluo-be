package com.golapadeok.fluo.domain.workspace.dto;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

@Getter
public class CustomPageImpl<T> extends PageImpl<T> {
    private final long nextCursor;

    public CustomPageImpl(List<T> content, Pageable pageable, long total, long nextCursor) {
        super(content, pageable, total);
        this.nextCursor = nextCursor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomPageImpl<?> that = (CustomPageImpl<?>) o;
        return nextCursor == that.nextCursor;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), nextCursor);
    }
}
