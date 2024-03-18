package com.golapadeok.fluo.domain.state;

import com.golapadeok.fluo.domain.state.domain.State;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class StateMapper {
    public StateDto convertStateToDto(State state) {
        return new StateDto(
                state.getId().toString(),
                state.getWorkspace().getId().toString(),
                state.getName()
        );
    }

    public List<StateDto> convertStateToDto(List<State> states) {
        Iterator<State> iterator = states.iterator();
        List<StateDto> results = new ArrayList<>();

        while (iterator.hasNext()) {
            State state = iterator.next();
            StateDto workspaceDto = convertStateToDto(state);
            results.add(workspaceDto);
        }

        return Collections.unmodifiableList(results);
    }
}
