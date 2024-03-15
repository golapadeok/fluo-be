package com.golapadeok.fluo.domain.workspace.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class State {
    @Id
    @GeneratedValue
    @Column(name = "WORKSPACE_STATE_ID")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "states")
    private Workspace workspace;


    public State(String name) {
        this.name = name;
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.getStates().add(this);
    }
}
