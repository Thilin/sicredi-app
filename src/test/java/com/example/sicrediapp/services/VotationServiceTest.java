package com.example.sicrediapp.services;

import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.impl.VotationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class VotationServiceTest {

    VotationService votationService;

    @MockBean
    VotationRepository votationRepository;

    @MockBean
    SessionRepository sessionRepository;

    @BeforeEach
    public void setUp(){
        this.votationService = new VotationServiceImpl(votationRepository, sessionRepository);
    }

    @Test
    @DisplayName("Should be able to count Votes")
    public void shouldCountVotesTest(){
        Long sessionId = 1L;
        var session = Session.builder().id(1L).build();
        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        Mockito.when(votationRepository.countBySessionIdAndVoteTrue(sessionId)).thenReturn(5L);
        Mockito.when(votationRepository.countBySessionIdAndVoteFalse(sessionId)).thenReturn(6L);

        var dto = votationService.countVotes(sessionId);

        assertThat(dto.getVotesYes()).isGreaterThanOrEqualTo(0);
        assertThat(dto.getVotesNo()).isGreaterThanOrEqualTo(0);
    }
}
