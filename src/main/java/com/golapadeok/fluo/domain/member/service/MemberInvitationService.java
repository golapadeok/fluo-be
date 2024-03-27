package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInvitationService {

    private final MemberRepository memberRepository;

    // 회원이 초대받은 워크스페이스 목록 조회
    public void getInvitationWorkspace() {

    }

}
