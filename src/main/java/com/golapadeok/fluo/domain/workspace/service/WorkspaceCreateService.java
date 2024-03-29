package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.util.InvitationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceCreateService {
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private static final String DEFAULT_IMAGE = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";

    @Transactional
    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        String invitationCode = extractedInvitationCode();

        //Workspace
        Workspace workspace = new Workspace(title, description, DEFAULT_IMAGE);
        workspace.changeInvitationCode(invitationCode);
        workspaceRepository.save(workspace);

        //State
        State state = new State("default", true);
        state.changeWorkspace(workspace);
        stateRepository.save(state);

        return new WorkspaceResponse(workspace);
    }

    private String extractedInvitationCode() {
        String generator = InvitationCodeGenerator.generator();
        while (workspaceRepository.existsByInvitationCode(generator)) {
            generator = InvitationCodeGenerator.generator();
        }
        return generator;
    }
}
