package com.golapadeok.fluo.domain.invitation.repository;

import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface InvitationQueryRepository {

    Page<Invitation> findMemberWithInvitationList(Long memberId, CursorPageRequest cursorPageRequest);

}
