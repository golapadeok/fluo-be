package com.golapadeok.fluo.domain.tag.repository;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
