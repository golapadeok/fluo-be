package com.golapadeok.fluo.common.scheduler;

import com.golapadeok.fluo.domain.social.domain.BlackList;
import com.golapadeok.fluo.domain.social.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlackListDeleteScheduler {

    private final BlackListRepository blackListRepository;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24, initialDelay = 1L) // 서버 실행 1초 후에 한번 실행하며, 하루마다 재실행
    public void deleteBlackListByAccessToken() {

        // 현재시간 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 블랙리스트 전체 가져오기
        List<BlackList> blackLists = this.blackListRepository.findAll();

        // 블랙리스트의 생성시간이 현재보다 작으면 삭제
        blackLists.forEach(blackList -> {
            LocalDateTime createDate = blackList.getCreateDate();

            if(createDate != null && createDate.isBefore(currentDateTime)){
                this.blackListRepository.delete(blackList);
            }
        });


    }

}
