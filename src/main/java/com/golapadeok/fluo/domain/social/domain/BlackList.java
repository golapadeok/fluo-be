package com.golapadeok.fluo.domain.social.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Entity
public class BlackList {

    @Id @GeneratedValue
    @Column(name = "BLACK_LSIT_ID")
    private Long id;

    private String accessToken;

    @Builder
    public BlackList(String accessToken) {
        this.accessToken = accessToken;
    }
}
