package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.util.DateUtils;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.request.InviteEmailRequest;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationEmailResponse;
import com.golapadeok.fluo.domain.invitation.dto.response.MemberInfo;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkspaceInviteMemberEmailService {

    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public InvitationEmailResponse InviteMemberEmail(String workspaceId, InviteEmailRequest request){

        Member member = this.memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_MEMBER));

        Workspace workspace = this.workspaceRepository.findById(Long.valueOf(workspaceId))
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_WORKSPACE));

        // 초대를 보낼때 워크스페이스에 소속된 회원이면 초대코드 발송 안되게 하기
        workspace.getWorkspaceMembers().stream()
                .filter(wm -> wm.getMember().getId().equals(member.getId()))
                .findFirst()
                .ifPresent(wm -> {
                    throw new InvitationException(InvitationErrorStatus.ALREADY_REGISTERED_MEMBER);
                });

        Invitation invitation = Invitation.builder()
                .member(member)
                .workspace(workspace)
                .build();
        Invitation saved = invitationRepository.save(invitation);

        return InvitationEmailResponse.builder()
                .invitationId(String.valueOf(saved.getId()))
                .workspaceId(workspaceId)
                .member(MemberInfo.of(member))
                .createDate(saved.getCreateDate())
                .build();
    }

}
