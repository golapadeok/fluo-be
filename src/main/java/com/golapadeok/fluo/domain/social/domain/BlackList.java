package com.golapadeok.fluo.domain.social.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BlackList {

    @Id @GeneratedValue
    @Column(name = "BLACK_LIST_ID")
    private Long id;

    private String accessToken;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    public BlackList(String accessToken) {
        this.accessToken = accessToken;
    }
}
