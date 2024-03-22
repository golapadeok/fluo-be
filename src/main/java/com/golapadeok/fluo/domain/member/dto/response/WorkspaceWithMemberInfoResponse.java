package com.golapadeok.fluo.domain.member.dto.response;


import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class WorkspaceWithMemberInfoResponse {

    private String memberId;
    private String profileImg;

    public static List<WorkspaceWithMemberInfoResponse> of(List<WorkspaceMember> workspaceMember) {
        List<WorkspaceWithMemberInfoResponse> response = new ArrayList<>();

        for (WorkspaceMember wm : workspaceMember) {
            WorkspaceWithMemberInfoResponse memberInfo = WorkspaceWithMemberInfoResponse.builder()
                    .memberId(String.valueOf(wm.getMember().getId()))
                    .profileImg(wm.getMember().getProfile())
                    .build();
            response.add(memberInfo);
        }

        return Collections.unmodifiableList(response);
    }
}
