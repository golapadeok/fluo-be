package com.golapadeok.fluo.common.scheduler;

import com.golapadeok.fluo.domain.social.domain.BlackList;
import com.golapadeok.fluo.domain.social.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BlackListDeleteScheduler {

    private final BlackListRepository blackListRepository;

    @Scheduled(cron = "0 0 0 * * *") // 자정마다 스케줄러가 돌도록 설정
    public void deleteBlackListByAccessToken() {

        // 현재시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 블랙리스트 전체 가져오기
        List<BlackList> blackLists = this.blackListRepository.findAll();

        // 블랙리스트의 생성시간이 현재보다 작으면 삭제
        blackLists.forEach(blackList -> {
            LocalDateTime createDate = blackList.getCreateDate();

            if(createDate != null && createDate.isBefore(now)){
                this.blackListRepository.delete(blackList);
            }
        });


    }

}
