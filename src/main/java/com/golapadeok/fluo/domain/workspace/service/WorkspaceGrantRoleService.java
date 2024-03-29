package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.dto.RoleDto;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceGrantRoleRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceGrantRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WorkspaceGrantRoleService {

    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public WorkspaceGrantRoleResponse grantRole(PrincipalDetails principalDetails, WorkspaceGrantRoleRequest request) {
        Member member = principalDetails.getMember();
        Role role = this.roleRepository.findById(Long.valueOf(request.getRole().getRoleId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 역할입니다."));

        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(role)
                .build();

        this.memberRoleRepository.save(memberRole);

        return new WorkspaceGrantRoleResponse(member, role);
    }

}
