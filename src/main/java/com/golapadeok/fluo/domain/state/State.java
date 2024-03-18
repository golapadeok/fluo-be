package com.golapadeok.fluo.domain.state;


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


    public State(String name) {
        this.name = name;
    }

    public void changeWorkspace(Workspace workspace) {
        this.workspace = workspace;
        workspace.getStates().add(this);
    }

    public void updateState(StateDto stateDto) {
        this.name = stateDto.getName();
    }

}
