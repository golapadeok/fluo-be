package com.golapadeok.fluo.domain.role.dto.request;

import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Schema(description = "역할 추가 요청 데이터")
public class RoleCreateRequest {

    @NotNull(message = "역할 이름은 필수 입력입니다.")
    @Schema(description = "역할의 이름입니다.", example = "name")
    private String name;

    @NotNull(message = "역할 설명은 필수 입력입니다.")
    @Schema(description = "역할의 설명입니다.", example = "description")
    private String description;

    @NotNull(message = "권한 이름은 필수 입력입니다.")
    @Schema(description = "역할에 속하는 권한의 이름 리스트입니다.", example = "[\"CREATE_ROLE\", \"ASSIGN_ROLE\"]")
    private List<Credential> credentials;

    public RoleCreateRequest(String name, String description, List<Credential> credentials) {
        this.name = name;
        this.description = description;
        this.credentials = credentials.stream().map(c -> Credential.from(c.toString())).toList();
    }

    public Role toEntity(Workspace workspace) {
        return Role.builder()
                .name(this.name)
                .description(this.description)
                .roles(String.join(",", credentials.stream().map(Enum::name).toList()))
                .workspace(workspace)
                .isDefault(false)
                .build();
    }

}
