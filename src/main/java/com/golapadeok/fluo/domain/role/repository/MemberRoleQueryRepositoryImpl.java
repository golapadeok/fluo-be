package com.golapadeok.fluo.domain.role.repository;

import com.golapadeok.fluo.domain.member.domain.QMember;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.QMemberRole;
import com.golapadeok.fluo.domain.role.domain.QRole;
import com.golapadeok.fluo.domain.workspace.domain.QWorkspace;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRoleQueryRepositoryImpl implements MemberRoleQueryRepository {

    private final JPAQueryFactory query;

    @Autowired
    public MemberRoleQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    // 워크스페이스 아이디와 멤버 아이디로 해당 워크스페이스에서 멤버가 어떤 역할을 가졌는지 확인하는 로직
    @Override
    public Optional<MemberRole> findWorkspaceWithMemberRoleList(Long memberId, Long workspaceId) {

        QMemberRole memberRole = QMemberRole.memberRole;
        QRole role = QRole.role;
        QWorkspace workspace = QWorkspace.workspace;

        MemberRole response = query.selectFrom(memberRole)
                .join(role).on(memberRole.role.id.eq(role.id))
                .join(workspace).on(role.workspace.id.eq(workspace.id))
                .where(memberRole.member.id.eq(memberId).and(workspace.id.eq(workspaceId)))
                .fetchOne();

        return Optional.ofNullable(response);

    }

}
