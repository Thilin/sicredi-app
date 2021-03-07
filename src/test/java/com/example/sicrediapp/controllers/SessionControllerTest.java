package com.example.sicrediapp.controllers;

import com.example.sicrediapp.api.controllers.SessionController;
import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.api.dtos.SessionResponseDTO;
import com.example.sicrediapp.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc
public class SessionControllerTest {

    String SESSION_API = "/sessions";

    @InjectMocks
    SessionController sessionController;

    @Autowired
    MockMvc mvc;

    @MockBean
    private SessionService sessionService;

    @BeforeEach
    public void setUp(){
        this.sessionController = new SessionController(sessionService);
    }

    @Test
    @DisplayName("Should create a Session")
    public void createSessionTest(){

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = SessionResponseDTO.builder().id(1L).duration(3L).scheduleId(1L).isOpen(false).build();
        var dto = SessionCreateDTO.builder().duration(1L).scheduleId(1L).build();
        Mockito.when(sessionService.save(dto)).thenReturn(response);
        ResponseEntity<Void> responseEntity = sessionController.create(dto);


        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    @DisplayName("Should get a Session by id")
    public void getSessioneByIdTest() throws Exception {
        Long id = 1L;

        var dto = SessionResponseDTO.builder().id(1L).duration(3L).scheduleId(1L).isOpen(false).build();

        BDDMockito.given(sessionService.findById(id)).willReturn(dto);

        var request = MockMvcRequestBuilders
                .get(SESSION_API+"/"+id)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("duration").value(dto.getDuration()));
    }

    @Test
    @DisplayName("Should return Not Found when the session does not exists")
    public void SessionNotFoundTest(){
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = SessionResponseDTO.builder().id(1L).duration(3L).scheduleId(1L).isOpen(false).build();
        var dto = SessionCreateDTO.builder().duration(1L).scheduleId(1L).build();
        Mockito.when(sessionService.findById(1L)).thenReturn(null);

        ResponseEntity<SessionResponseDTO> responseEntity = sessionController.findById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should get all Sessions")
    public void getAllSessionsTest() throws Exception {

        List<SessionResponseDTO> list= new ArrayList<>();
        var dto1 = SessionResponseDTO.builder().id(1L).duration(3L).scheduleId(1L).isOpen(false).build();
        var dto2 = SessionResponseDTO.builder().id(2L).duration(3L).scheduleId(1L).isOpen(false).build();
        list.add(dto1);
        list.add(dto2);

        BDDMockito.given(sessionService.findAll()).willReturn(list);

        var request = MockMvcRequestBuilders
                .get(SESSION_API+"/all")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
