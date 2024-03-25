package com.golapadeok.fluo.domain.social.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BlackList {

    @Id @GeneratedValue
    @Column(name = "BLACK_LIST_ID")
    private Long id;

    private String accessToken;

    public BlackList(String accessToken) {
        this.accessToken = accessToken;
    }
}
