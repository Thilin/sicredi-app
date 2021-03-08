package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.controllers.VotationController;
import com.example.sicrediapp.api.dtos.*;
import com.example.sicrediapp.services.AssociateService;
import com.example.sicrediapp.services.ScheduleService;
import com.example.sicrediapp.services.SessionService;
import com.example.sicrediapp.services.VotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(VotationController.class)
@AutoConfigureMockMvc
public class VotationControllerTest {

    String VOTATION_API = "/votations";

    @InjectMocks
    VotationController votationController;

    @Autowired
    MockMvc mvc;

    @MockBean
    private VotationService votationService;

    @MockBean
    SessionService sessionService;
    @MockBean
    AssociateService associateService;
    @MockBean
    ScheduleService scheduleService;

    @BeforeEach
    public void setUp(){
        this.votationController = new VotationController(votationService);
    }

    @Test
    @DisplayName("Should get a vote result by id")
    public void getVoteResultByIdTest(){

        Long sessionId = 1L;
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var dto = VoteCountDTO.builder().votesYes(3L).votesNo(1L).build();;
        Mockito.when(votationService.countVotes(1L)).thenReturn(dto);

        ResponseEntity<VoteCountDTO> responseEntity = votationController.votationResult(sessionId);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    @DisplayName("Should return Not Found when the session does not exists when trying to count the votes")
    public void voteResultNotFoundTest(){
        Long sessionId = 1L;
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var dto = new VoteCountDTO();
        Mockito.when(votationService.countVotes(1L)).thenReturn(null);

        ResponseEntity<VoteCountDTO> responseEntity = votationController.votationResult(sessionId);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }


    @Test
    @DisplayName("Should vote with success")
    public void shouldVoteWithSuccessTest(){
        Long sessionId = 1L;
        Long associateId = 1L;
        Long scheduleId = 1L;
        boolean vote = false;
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var associate = AssociateResponseDTO.builder().id(1L).build();
        Mockito.when(associateService.findById(1L)).thenReturn(associate);
        var session = SessionResponseDTO.builder().id(1L).build();
        Mockito.when(sessionService.findById(1L)).thenReturn(session);
        var schedule = ScheduleResponseDTO.builder().id(1L).build();
        Mockito.when(scheduleService.findById(1L)).thenReturn(schedule);

        var dto = VoteDTO.builder().associateId(associateId).sessionId(sessionId).vote("NÃO").scheduleId(scheduleId).build();
        Mockito.when(votationService.vote(sessionId, vote, associateId)).thenReturn(dto);

        ResponseEntity<VoteDTO> responseEntity = votationController.vote(sessionId, vote, associateId);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(dto);
    }

    @Test
    @DisplayName("Should return Not Found when try to vote and session or associate does not exists")
    public void sessionOrAssociateNotFoundTest(){
        Long sessionId = 1L;
        Long associateId = 1L;
        boolean vote = false;
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(votationService.vote(sessionId, vote, associateId)).thenReturn(null);

        ResponseEntity<VoteDTO> responseEntity = votationController.vote(sessionId, vote, associateId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
