package com.golapadeok.fluo.domain.role.service;

import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.dto.request.RoleCreateRequest;
import com.golapadeok.fluo.domain.role.dto.request.RoleUpdateRequest;
import com.golapadeok.fluo.domain.role.dto.response.*;
import com.golapadeok.fluo.domain.role.exception.RoleErrorStatus;
import com.golapadeok.fluo.domain.role.exception.RoleException;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.WorkspaceDto;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceRequest;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final WorkspaceRepository workspaceRepository;

    public BaseResponse getAllCredential(){
        return new BaseResponse(CredentialResponse.toItemList());
    }

    @Transactional
    public BaseResponse getWorkspaceRoleList(Integer workspaceId) {
       List<Role> roles = this.roleRepository.findByWorkspaceId(workspaceId.longValue())
                .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_WORKSPACE));
        log.info("role : {}", roles.toString());

        List<WorkspaceRoleListResponse> response = roles.stream()
                .map(role -> WorkspaceRoleListResponse.builder()
                        .roleId(String.valueOf(role.getId()))
                        .name(role.getName())
                        .credentials(role.getRoleList().stream()
                                .map(r -> new CredentialResponse(Credential.valueOf(r.trim()).getName(), Credential.valueOf(r.trim()).getDescription()))
                                .toList())
                        .build())
                .toList();
        log.info("response : {}", response);

        return new BaseResponse(response);
    }

    @Transactional
    public CreateRoleResponse createWorkspaceRole(Integer workspaceId, RoleCreateRequest request) {
        // 워크스페이스 존재여부 확인
        Workspace workspace = this.workspaceRepository.findById(Long.valueOf(workspaceId))
                .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_WORKSPACE));

        // 해당 워크스페이스에 같은 역할이름이 있는지를 검증
        roleRepository.findByNameAndWorkspaceId(request.getName(), workspace.getId())
                .ifPresent(role -> {
                    throw new RoleException(RoleErrorStatus.DUPLICATION_NAME);
                });

        Role role = request.toEntity(workspace);
        return new CreateRoleResponse(String.valueOf(this.roleRepository.save(role).getId()));
    }

    @Transactional
    public RoleDeleteResponse deleteWorkspaceRole(Integer roleId) {
        Role role = this.roleRepository.findById(Long.valueOf(roleId))
                .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_ROLE));

        this.roleRepository.delete(role);

        String message = "역할이 삭제되었습니다.";
        return new RoleDeleteResponse(message);
    }

    @Transactional
    public UpdateRoleResponse updateWorkspaceRole(Integer workspaceId, Integer roleId, RoleUpdateRequest request) {
        Role role = this.roleRepository.findById(Long.valueOf(roleId))
                .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_ROLE));

        // 역할 업데이트
        role.updateRole(request);



        return UpdateRoleResponse.builder()
                .workspaceId(String.valueOf(workspaceId))
                .items(null)
                .build();
    }
}
