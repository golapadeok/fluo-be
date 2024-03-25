package com.golapadeok.fluo.domain.file.domain;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultImage {

    @Id
    @GeneratedValue
    private Long id;
    private String url;

    @JoinColumn(name = "WORKSPACE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    public DefaultImage(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public DefaultImage(String url) {
        this(null, url);
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
    public void changeUrl(String url){
        this.url = url;
    }
}
