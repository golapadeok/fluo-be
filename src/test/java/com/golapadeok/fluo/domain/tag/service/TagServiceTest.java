package com.golapadeok.fluo.domain.tag.service;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagDeleteResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagSearchResponse;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@DisplayName("태그 서비스 단위 테스트")
class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("태그 조회 성공 케이스")
    void searchTags() {
        //given
        Integer id = 1;

        //when
        given(tagRepository.findById(id.longValue()))
                .willReturn(Optional.of(new Tag(1L, "tag1", "######")));
        //then
        TagSearchResponse response = tagService.searchTags(id);

        assertThat(response).isNotNull();
        assertThat(response.getTagId()).isEqualTo("1");
        assertThat(response.getTagName()).isEqualTo("tag1");
        assertThat(response.getColorCode()).isEqualTo("######");
    }

    @Test
    @DisplayName("태그 추가 성공 케이스")
    void createTags() {
        //given
        TagCreateRequest request = new TagCreateRequest(1, "tag1", "######");
        //when
        given(workspaceRepository.findById(request.getWorkspaceId().longValue()))
                .willReturn(Optional.of(new Workspace(1L, "title", "description", "url")));

        given(tagRepository.save(any(Tag.class))).willReturn(new Tag(1L, "tag1", "######"));
        //then
        TagCreateResponse response = tagService.createTags(request);

        assertThat(response).isNotNull();
        assertThat(response.getTagId()).isEqualTo("1");
        assertThat(response.getTagName()).isEqualTo("tag1");
        assertThat(response.getColorCode()).isEqualTo("######");

    }

    @Test
    @DisplayName("태그 삭제 성공 케이스")
    void deleteTags() {
        //given
        Integer id = 1;

        Workspace workspace = new Workspace(1L, "", "", "");

        Tag tag = new Tag(1L, "tag1", "######");
        tag.changeWorkspace(workspace);

        Task task = new Task(1L, "", "", "", null, null);
        task.changeTag(tag);
        task.changeWorkspace(workspace);


        //when
        given(tagRepository.findById(id.longValue()))
                .willReturn(Optional.of(tag));

        given(taskRepository.findByTagId(1L)).willReturn(List.of(task));

        //then
        TagDeleteResponse response = tagService.deleteTags(id);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("태그 삭제에 성공했습니다.");
    }
}