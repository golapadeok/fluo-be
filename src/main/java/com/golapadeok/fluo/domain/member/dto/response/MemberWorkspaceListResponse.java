package com.golapadeok.fluo.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class MemberWorkspaceListResponse {

    private String total;
    private String nextPageNum;
    private List<WorkspaceInfoResponse> items;

}