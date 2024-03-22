package com.golapadeok.fluo.domain.role.dto.request;

import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Schema(description = "역할 수정 요청 데이터")
public class RoleUpdateRequest {

    @NotNull(message = "역할 이름은 필수 입력입니다.")
    @Schema(description = "역할의 이름입니다.", example = "name")
    private String name;

    @NotNull(message = "권한 이름은 필수 입력입니다.")
    @Schema(description = "역할에 속하는 권한의 이름 리스트입니다.", example = "[\"ENTER\", \"USER_INVITE\"]")
    private List<String> credentials;

    public Role toEntity(Workspace workspace) {
        return Role.builder()
                .name(this.name)
                .roles(String.join(",", credentials))
                .workspace(workspace)
                .build();
    }

}
