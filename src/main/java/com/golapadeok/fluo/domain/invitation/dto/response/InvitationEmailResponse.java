package com.golapadeok.fluo.domain.invitation.dto.response;

import com.golapadeok.fluo.common.util.DateUtils;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationEmailResponse {

    private String invitationId;
    private String workspaceId;
    private MemberInfo member;
    private String createDate;

    @Builder
    public InvitationEmailResponse(String invitationId, String workspaceId, MemberInfo member, LocalDateTime createDate) {
        this.invitationId = invitationId;
        this.workspaceId = workspaceId;
        this.member = member;
        this.createDate = DateUtils.dateFormatter(createDate);
    }
}
