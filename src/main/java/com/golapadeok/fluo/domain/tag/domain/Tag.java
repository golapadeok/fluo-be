package com.golapadeok.fluo.domain.tag.domain;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "TAG_ID")
    private Long id;

    private String tagName;
    private String colorCode;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    public Tag(Long id, String tagName, String colorCode) {
        this.id = id;
        this.tagName = tagName;
        this.colorCode = colorCode;
    }

    public Tag(String tagName, String colorCode) {
        this(null, tagName, colorCode);
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.getTags().add(this);
    }
}
