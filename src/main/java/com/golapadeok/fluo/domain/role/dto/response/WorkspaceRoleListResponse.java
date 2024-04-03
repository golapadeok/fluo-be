package com.golapadeok.fluo.domain.role.dto.response;

import com.golapadeok.fluo.domain.role.domain.Credential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class WorkspaceRoleListResponse {

    private String roleId;
    private String name;
    private String description;
    private List<String> credentials;

}
