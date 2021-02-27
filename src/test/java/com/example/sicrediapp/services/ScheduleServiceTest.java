package com.example.sicrediapp.services;

import com.example.sicrediapp.domains.Schedule;
import com.example.sicrediapp.repositories.ScheduleRepository;
import com.example.sicrediapp.services.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
        var schedule = Schedule.builder().duration(1L).isOpen(false).build();
        Mockito.when(scheduleRepository.save(schedule))
                .thenReturn(Schedule.builder().id(1L).duration(1L).isOpen(false).build());

        var savedSchedule = scheduleService.save(schedule);

        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getDuration()).isEqualTo(1L);
        assertThat(savedSchedule.isOpen()).isFalse();
    }
}
