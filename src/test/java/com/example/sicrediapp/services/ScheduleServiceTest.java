package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.services.impl.ScheduleServiceImpl;
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
public class ScheduleServiceTest {

    ScheduleService scheduleService;

    @MockBean
    ScheduleRepository scheduleRepository;

    @BeforeEach
    public void setUp(){
        this.scheduleService = new ScheduleServiceImpl(scheduleRepository);
    }

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

    @Test
    @DisplayName("Should return a schedule by Id")
    public void findAssociateByIdTest(){
        Long id = 1L;
        var schedule = Schedule.builder().id(id).description("Dividendos").build();

        Mockito.when(scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));

        var dto = scheduleService.findById(id);

        assertThat(dto.getDescription()).isEqualTo(schedule.getDescription());
    }
}
