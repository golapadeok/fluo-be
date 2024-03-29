package com.golapadeok.fluo.domain.invitation.repository;

import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.domain.QInvitation;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.member.domain.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class InvitationQueryRepositoryImpl implements InvitationQueryRepository {

    private final JPAQueryFactory query;
    private final QInvitation invitation;

    public InvitationQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
        this.invitation = QInvitation.invitation;
    }

    @Override
    public Page<Invitation> findMemberWithInvitationList(Long memberId, CursorPageRequest cursorPageRequest) {
        List<Invitation> invitations = query.selectFrom(invitation)
                .where(eqInvitationId(Long.valueOf(cursorPageRequest.getCursorId()), memberId))
                .orderBy(invitation.createDate.desc())
                .limit(cursorPageRequest.getLimit())
                .fetch();

        Long total = query.select(invitation.count())
                .from(invitation)
                .where(eqMemberId(memberId))
                .fetchOne();

        return new PageImpl<>(invitations, PageRequest.of(0, cursorPageRequest.getLimit()), total);
    }

    private BooleanExpression eqInvitationId(Long invitationId, Long memberId) {
        if(invitationId == 0 || invitationId == null) {
            return invitation.member.id.eq(memberId).and(invitation.isPending.isNull());
        }
        return invitation.id.lt(invitationId).and(invitation.member.id.eq(memberId).and(invitation.isPending.isNull()));
    }

    private BooleanExpression eqMemberId(Long memberId) {
        if(memberId != null) {
            return invitation.member.id.eq(memberId).and(invitation.isPending.isNull());
        }
        return null;
    }
}
