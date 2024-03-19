package com.golapadeok.fluo.domain.role.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.role.dto.response.CredentialAllResponse;
import com.golapadeok.fluo.domain.role.dto.response.WorkspaceRoleListResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public BaseResponse getAllCredential(){
        return new BaseResponse(CredentialAllResponse.toItemList());
    }

    @Transactional
    public BaseResponse getWorkspaceRoleList(Integer workspaceId) {
        List<Role> roles = this.roleRepository.findByWorkspaceId(workspaceId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스 입니다."));
        log.info("role : {}", roles.toString());

        List<WorkspaceRoleListResponse> response = roles.stream()
                .map(role -> WorkspaceRoleListResponse.builder()
                        .roleId(String.valueOf(role.getId()))
                        .name(role.getName())
                        .credentials(role.getRoleList().stream()
                                .map(r -> {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("name", Credential.valueOf(r.trim()).getName());
                                    map.put("description", Credential.valueOf(r.trim()).getDescription());
                                    return map;
                                })
                                .toList()
                        ).build()
                ).toList();
        log.info("response : {}", response);

        return new BaseResponse(response);
    }

}
