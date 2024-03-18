package com.golapadeok.fluo.domain.state.domain;


import com.golapadeok.fluo.domain.state.StateDto;
import com.golapadeok.fluo.domain.state.dto.request.StateUpdateRequest;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
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

    @Column(name = "STATE_ID")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "states")
    private Workspace workspace;

    public State(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public State(String name) {
        this(null, name);
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.getStates().add(this);
    }

    public void changeState(StateUpdateRequest request) {
        this.name = request.getName();
    }

}
