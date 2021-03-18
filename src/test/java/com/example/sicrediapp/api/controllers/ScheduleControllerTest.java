package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleResponseDTO;
import com.example.sicrediapp.services.ScheduleService;
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
@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc
public class ScheduleControllerTest {

    String SCHEDULE_API = "/schedules";

    @InjectMocks
    ScheduleController scheduleController;

    @Autowired
    MockMvc mvc;

    @MockBean
    private ScheduleService scheduleService;

    @BeforeEach
    public void setUp(){
        this.scheduleController = new ScheduleController(scheduleService);
    }

    @Test
    @DisplayName("Should create a Schedule")
    public void createScheduleTest(){

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = ScheduleResponseDTO.builder().id(1L).description("dividendos").build();
        var dto = ScheduleDTO.builder().description("dividendos").build();
        Mockito.when(scheduleService.save(dto)).thenReturn(response);
        ResponseEntity<Void> responseEntity = scheduleController.create(dto);


        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    @DisplayName("Should get a Schedule by id")
    public void getScheduleByIdTest() throws Exception {
        Long id = 1L;

        var dto = ScheduleResponseDTO.builder().description("Dividendos").build();

        BDDMockito.given(scheduleService.findById(id)).willReturn(dto);

        var request = MockMvcRequestBuilders
                .get(SCHEDULE_API+"/"+id)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").value(dto.getDescription()));
    }

    @Test
    @DisplayName("Should return Not Found when the schedule does not exists")
    public void ScheduleNotFoundTest(){
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = ScheduleResponseDTO.builder().id(1L).description("dividendos").build();
        var dto = ScheduleDTO.builder().description("dividendos").build();
        Mockito.when(scheduleService.findById(1L)).thenReturn(null);

        ResponseEntity<ScheduleResponseDTO> responseEntity = scheduleController.findById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should get all Schedules")
    public void getAllSchedulesTest() throws Exception {

        List<ScheduleResponseDTO> list= new ArrayList<>();
        var dto1 = ScheduleResponseDTO.builder().description("Dividendos").build();
        var dto2 = ScheduleResponseDTO.builder().description("Crédito").build();
        list.add(dto1);
        list.add(dto2);

        BDDMockito.given(scheduleService.findAll()).willReturn(list);

        var request = MockMvcRequestBuilders
                .get(SCHEDULE_API+"/all")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
