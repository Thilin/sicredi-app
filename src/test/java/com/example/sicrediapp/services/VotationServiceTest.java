package com.example.sicrediapp.services;

import com.example.sicrediapp.api.exceptions.CountVoteSessionOpenException;
import com.example.sicrediapp.api.exceptions.DuplicateVoteSameSessionException;
import com.example.sicrediapp.api.exceptions.SessionClosedException;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.entity.Votation;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.impl.VotationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class VotationServiceTest {

    VotationService votationService;

    @MockBean
    VotationRepository votationRepository;

    @MockBean
    SessionRepository sessionRepository;

    @MockBean
    AssociateRepository associateRepository;

    @MockBean
    CheckCPFService checkCPFService;

    @BeforeEach
    public void setUp(){
        this.votationService = new VotationServiceImpl(votationRepository, sessionRepository, associateRepository, checkCPFService);
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

    @Test
    @DisplayName("Should return an exception when tries to count the vote of a opened session")
    public void shouldThrowCountVoteSessionOpenExceptionTest(){
        var session = Session.builder().id(1L).isOpen(true).build();
        Mockito.when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(CountVoteSessionOpenException.class,
                () -> votationService.countVotes(session.getId()));

        String expectedMessage = COUNT_VOTE_SESSION_OPEN.getDescription();
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Should vote with sucess")
    public void shouldVoteWithSuccessTest(){
        boolean vote = false;
        var session = Session.builder().id(1L).isOpen(true).build();
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        var associate = Associate.builder().id(1L).build();
        Mockito.when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));

        var votation = Votation.builder().associate(associate).vote(vote).session(session).build();
        var savedVotation = Votation.builder().id(1L).associate(associate).vote(vote).session(session).build();

        Mockito.when(votationRepository.save(votation)).thenReturn(savedVotation);

        votationService.vote(session.getId(), vote, associate.getId());

        assertThat(savedVotation.getId()).isNotNull();
        assertThat(session.isOpen()).isTrue();
        assertThat(savedVotation.getVote()).isNotNull();
    }

    @Test
    @DisplayName("Should throw an exception when try to vote an the session is closed")
    public void shouldThrowExceptionWhenVoteSessionClosedTest(){

        boolean vote = false;
        var session = Session.builder().id(1L).isOpen(false).build();
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        var associate = Associate.builder().id(1L).build();
        Mockito.when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(SessionClosedException.class, () -> votationService.vote(session.getId(), vote, associate.getId()));
        String expectedMessage = SESSION_CLOSED.getDescription();
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);

    }

    @Test
    @DisplayName("Throws an exception when a same associate tries to vote on the same session twice")
    public void shouldThrowDuplicatedVoteSameSessionExceptionTest(){
        boolean vote = false;
        var session = Session.builder().id(1L).isOpen(true).build();
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        var associate = Associate.builder().id(1L).build();
        Mockito.when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        var votation = Votation.builder().session(session).vote(vote).associate(associate).build();

        Mockito.when(votationRepository.findBySessionIdAndAssociateId(session.getId(), associate.getId())).thenReturn(votation);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(DuplicateVoteSameSessionException.class, () -> votationService.vote(session.getId(), vote, associate.getId()));
        String expectedMessage = DUPLICATE_VOTE_SAME_SESSION.getDescription();
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
