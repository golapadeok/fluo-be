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
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // 1을 더하는 이유는 이번에 생성할 역할을 의미
        if(workspace.getRoles().size() + 1 > 6) {
            throw new RoleException(RoleErrorStatus.LIMIT_EXCEEDED_FOR_CREATION);
        }

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

        // 역할의 이름이 관리자이고, 모든 권한을 갖고있으면(개수) 수정 못하도록 막기
        if(role.getName().equals("관리자") && role.getRoleList().size() == 10) {
            throw new RoleException(RoleErrorStatus.NOT_DELETE_ADMIN_ROLE);
        }

        this.roleRepository.delete(role);

        String message = "역할이 삭제되었습니다.";
        return new RoleDeleteResponse(message);
    }

    @Transactional
    public UpdateRoleResponse updateWorkspaceRole(Integer workspaceId, Integer roleId, RoleUpdateRequest request) {
        Role role = this.roleRepository.findById(Long.valueOf(roleId))
                .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_ROLE));

        log.info("role : {}", role.toString());

        // 역할의 이름이 관리자이고, 모든 권한을 갖고있으면(개수) 수정 못하도록 막기
        if(role.getName().equals("관리자") && role.getRoleList().size() == 10) {
            throw new RoleException(RoleErrorStatus.NOT_UPDATE_ADMIN_ROLE);
        }

        // 역할 업데이트
        role.updateRole(request);
        
        // 해당 역할의 권한들의 이름과 설명을 리스트에 담아준다.
        List<CredentialResponse> credentialResponses = role.getRoleList().stream()
                .map(r -> CredentialResponse.builder()
                        .name(Credential.valueOf(r.trim()).getName())
                        .description(Credential.valueOf(r.trim()).getDescription())
                        .build())
                .toList();

        // 해당 워크스페이스의 역할과 해당하는 권한들의 이름과 설명을 출력한다.
        WorkspaceRoleListResponse workspaceRoleListResponse = WorkspaceRoleListResponse.builder()
                .roleId(String.valueOf(role.getId()))
                .name(role.getName())
                .credentials(credentialResponses)
                .build();

        return UpdateRoleResponse.builder()
                .workspaceId(String.valueOf(workspaceId))
                .items(workspaceRoleListResponse)
                .build();
    }
}
