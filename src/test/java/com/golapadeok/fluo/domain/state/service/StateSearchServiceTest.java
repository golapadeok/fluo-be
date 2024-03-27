//package com.golapadeok.fluo.domain.state.service;
//
//import com.golapadeok.fluo.domain.state.domain.State;
//import com.golapadeok.fluo.domain.state.dto.response.StateSearchResponse;
//import com.golapadeok.fluo.domain.state.repository.StateRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.swing.text.html.Option;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.*;
//
//@Transactional
//@ExtendWith(MockitoExtension.class)
//class StateSearchServiceTest {
//    @InjectMocks
//    private StateSearchService stateSearchService;
//
//    @Mock
//    private StateRepository stateRepository;
//
//    @Test
//    void search() {
//        //given
//        State state = new State(1L, "state");
//        //when
//        given(stateRepository.findById(1L)).willReturn(Optional.of(state));
//
//        //then
//        StateSearchResponse search = stateSearchService.search(1);
//
//        assertThat(search).isNotNull();
//        assertThat(search.getStateId()).isEqualTo("1");
//        assertThat(search.getName()).isEqualTo("state");
//    }
//}