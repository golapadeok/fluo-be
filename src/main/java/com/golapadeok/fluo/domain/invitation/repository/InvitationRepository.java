package com.golapadeok.fluo.domain.invitation.repository;

import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Page<Invitation> findByMemberIdOrderByCreateDateDesc(Long memberId, Pageable pageable);
    Page<Invitation> findByIdLessThanAndMemberIdOrderByCreateDateDesc(Long id, Long memberId, Pageable pageable);

    Long countByMemberId(Long memberId);

}
