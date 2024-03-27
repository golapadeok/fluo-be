//package com.golapadeok.fluo.domain.state.service;
//
//import com.golapadeok.fluo.domain.state.domain.State;
//import com.golapadeok.fluo.domain.state.dto.request.StateCreateRequest;
//import com.golapadeok.fluo.domain.state.dto.response.StateCreateResponse;
//import com.golapadeok.fluo.domain.state.repository.StateRepository;
//import com.golapadeok.fluo.domain.workspace.domain.Workspace;
//import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.*;
//
//@Transactional
//@ExtendWith(MockitoExtension.class)
//class StateCreateServiceTest {
//    @InjectMocks
//    private StateCreateService stateCreateService;
//
//    @Mock
//    private StateRepository stateRepository;
//    @Mock
//    private WorkspaceRepository workspaceRepository;
//
//
//    @Test
//    @DisplayName("상태 생성 성공 케이스")
//    void create() {
//        //given
//        StateCreateRequest stateCreateRequest = new StateCreateRequest(1, "state");
//        Workspace workspace = new Workspace(1L, "title", "title", "description");
////        State state = new State(1L, "state");
//
//        //when
////        given(stateRepository.save(any(State.class))).willReturn(state);
//        given(workspaceRepository.findById(1L)).willReturn(Optional.of(workspace));
//
//        //then
//        StateCreateResponse stateCreateResponse = stateCreateService.create(stateCreateRequest);
//        assertThat(stateCreateResponse.getStateId()).isEqualTo("1");
//        assertThat(stateCreateResponse.getName()).isEqualTo("state");
//    }
//
//    @Test
//    void test() {
//        //given
//        List<Integer> newMember = List.of(1, 2, 3);
//        List<Integer> oldMember = List.of(1, 2, 4);
//
//        //when
//        List<Integer> list = newMember.stream()
//                .filter(integer -> oldMember.stream().noneMatch(id -> Objects.equals(id, integer)))
//                .toList();
//
//        List<Integer> list1 = oldMember.stream()
//                .filter(integer -> newMember.stream().noneMatch(id -> Objects.equals(id, integer))).toList();
//
//        //then
//        Assertions.assertThat(list).hasSize(1);
//        Assertions.assertThat(list.get(0)).isEqualTo(3);
//
//        Assertions.assertThat(list1).hasSize(1);
//        Assertions.assertThat(list1.get(0)).isEqualTo(4);
//    }
//}