package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ScheduleServiceTest {

    @MockBean
    ScheduleService scheduleService;

    @MockBean
    ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("Should create a new schedule")
    public void saveScheduleTest(){
        var schedule = Schedule.builder().description("Dividendos").build();
        var dto = ScheduleDTO.builder().description("Dividendos").build();
        var savedSchedule = Schedule.builder().id(1L).description("Dividendos").build();
        Mockito.when(scheduleRepository.save(schedule))
                .thenReturn(savedSchedule);

        scheduleService.save(dto);

        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getDescription()).isEqualTo("Dividendos");
    }
}
