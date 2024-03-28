package com.golapadeok.fluo.domain.tag.repository;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from Tag t where t.id = :tagId and t.workspace.id = :workspaceId")
    Optional<Tag> findByIdInAndWorkspaceId(@Param("tagId") Integer tagId, @Param("workspaceId") long workspaceId);
}
