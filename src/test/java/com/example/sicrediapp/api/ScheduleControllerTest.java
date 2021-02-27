//package com.example.sicrediapp.api;
//
//import com.example.sicrediapp.model.entity.Schedule;
//import com.example.sicrediapp.api.dtos.ScheduleDTO;
//import com.example.sicrediapp.api.exceptions.InvalidScheduleDurationException;
//import com.example.sicrediapp.services.ScheduleService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//@WebMvcTest
//@AutoConfigureMockMvc
//public class ScheduleControllerTest {
//
//    static String SCHEDULE_API = "/api/schedules";
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    ScheduleService scheduleService;
//
//    @Test
//    @DisplayName("Create a new schedule with success")
//    public void createScheduleTest() throws Exception {
//
//        var dto = ScheduleDTO.builder()
//                .duration(1L)
//                .isOpen(false)
//                .build();
//
//        var savedSchedule = Schedule.builder()
//                .id(1L)
//                .duration(1L)
//                .isOpen(false)
//                .build();
//
//        BDDMockito.given(scheduleService.save(Mockito.any(Schedule.class))).willReturn(savedSchedule);
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        var request = MockMvcRequestBuilders
//                .post(SCHEDULE_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc.perform(request).andExpect(status().isCreated())
//                .andExpect(jsonPath("id").value(1L))
//                .andExpect(jsonPath("duration").value(dto.getDuration()))
//                .andExpect(jsonPath("isOpen").value(dto.getIsOpen()));
//    }
//
//    @Test
//    @DisplayName("Throws an error when there is not enough information to create a schedule")
//    public void createInvalidScheduleTest() throws Exception{
//        String json = new ObjectMapper().writeValueAsString(new ScheduleDTO());
//
//        var request = MockMvcRequestBuilders
//                .post(SCHEDULE_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc.perform(request)
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errors", hasSize(2)));
//    }
//
//    @Test
//    @DisplayName("Should not be able to create a schedule with less than 1 minute of duration")
//    public void createScheduleWithInvalidDurationTest() throws Exception{
//        var dto = ScheduleDTO.builder()
//                .duration(0L)
//                .isOpen(false)
//                .build();
//
//        String json = new ObjectMapper().writeValueAsString(dto);
//        String messageErro = "The duration is less than 1 minute";
//        BDDMockito.given(scheduleService.save(Mockito.any(Schedule.class))).willThrow(new InvalidScheduleDurationException(messageErro));
//
//        var request = MockMvcRequestBuilders
//                .post(SCHEDULE_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mvc.perform(request)
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("errors", hasSize(1)))
//                .andExpect(jsonPath("errors[0]").value(messageErro));
//
//    }
//
//    private ScheduleDTO buildScheduleDTO() {
//        return ScheduleDTO.builder()
//                .duration(1L)
//                .isOpen(false)
//                .build();
//    }
//}
