package com.golapadeok.fluo.domain.social.repository;

import com.golapadeok.fluo.domain.social.domain.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    public Optional<BlackList> findByAccessToken(String accessToken);

}
