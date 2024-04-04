package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceGrantRoleRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceGrantRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WorkspaceGrantRoleService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public WorkspaceGrantRoleResponse grantRole(WorkspaceGrantRoleRequest request) {
        
        Member member = this.memberRepository.findById(request.getMemberId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Role role = this.roleRepository.findById(Long.valueOf(request.getRole().getRoleId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 역할입니다."));

        Long workspaceId = role.getWorkspace().getId();

        member.getMemberRoles().stream()
                .filter(mr -> mr.getRole().getWorkspace().getId().equals(workspaceId))
                .forEach(mr -> {
                    mr.updateRole(member, role);
                });

//        MemberRole memberRole = MemberRole.builder()
//                .member(member)
//                .role(role)
//                .build();

//        this.memberRoleRepository.save(memberRole);

        return new WorkspaceGrantRoleResponse(member, role);
    }

}
