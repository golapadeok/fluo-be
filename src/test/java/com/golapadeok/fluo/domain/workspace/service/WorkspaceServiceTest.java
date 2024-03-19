//package com.golapadeok.fluo.domain.workspace.service;
//
//import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
//import com.golapadeok.fluo.domain.workspace.util.WorkspaceMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class WorkspaceServiceTest {
//    @Mock
//    private WorkspaceRepository workspaceRepository;
//    private WorkspaceService workspaceService;
//
//    @BeforeEach
//    void setUp() {
////        workspaceService = new WorkspaceService(workspaceRepository, new WorkspaceMapper());
//    }
//
//    @Test
//    @DisplayName("워크스페이스 목록 전체조회")
//    void getWorkspaces() {
//        //when
//        workspaceService.getWorkspaces();
//
//        //then
//        verify(workspaceRepository).findAll();
//
//    }
//
//    @Test
//    @DisplayName("워크스페이스 단일 조회")
//    void getWorkspace() {
//        //when
//        workspaceService.getWorkspace(1);
//
//        //then
//        verify(workspaceRepository).findById(1L);
//    }
//}