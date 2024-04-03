package com.golapadeok.fluo.domain.tag.domain;

import com.golapadeok.fluo.domain.tag.dto.ColorCode;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Enumerated(EnumType.STRING)
    private ColorCode colorCode;

    @JoinColumn(name = "WORKSPACE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    public Tag(Long id, String tagName, ColorCode colorCode) {
        this.id = id;
        this.tagName = tagName;
        this.colorCode = colorCode;
    }

    @Builder(toBuilder = true)
    public Tag(String tagName, ColorCode colorCode) {
        this(null, tagName, colorCode);
    }

    public void changeTag(Tag tag) {
        this.tagName = tag.getTagName();
        this.colorCode = tag.colorCode;
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.getTags().add(this);
    }
}
