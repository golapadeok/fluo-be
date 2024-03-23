package com.golapadeok.fluo.domain.tag.repository;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.id in (:tagId) and t.workspace.id = :workspaceId")
    List<Tag> findByIdInAndWorkspaceId(@Param("tagId") List<Integer> tagId, @Param("workspaceId") long workspaceId);
}
