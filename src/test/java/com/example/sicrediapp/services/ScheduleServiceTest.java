package com.example.sicrediapp.services;

import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.api.exceptions.InvalidScheduleDurationException;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.services.impl.ScheduleServiceImpl;
import org.assertj.core.api.Assertions;
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
//
//    ScheduleService scheduleService;
//
//    @MockBean
//    ScheduleRepository scheduleRepository;
//
//    @BeforeEach
//    public void setUp(){
//        this.scheduleService = new ScheduleServiceImpl(scheduleRepository);
//    }
//
//    @Test
//    @DisplayName("Should create a new schedule")
//    public void saveScheduleTest(){
//        var schedule = buildValidSchedule();
//        Mockito.when(scheduleRepository.save(schedule))
//                .thenReturn(Schedule.builder().id(1L).duration(1L).isOpen(false).build());
//
//        var savedSchedule = scheduleService.save(schedule);
//
//        assertThat(savedSchedule.getId()).isNotNull();
//        assertThat(savedSchedule.getDuration()).isEqualTo(1L);
//        assertThat(savedSchedule.isOpen()).isFalse();
//    }
//
//    @Test
//    @DisplayName("Should not be able to create a schedule with less than 1 minute of duration")
//    public void createScheduleWithInvalidDurationTest(){
//        var schedule = Schedule.builder().duration(0L).isOpen(false).build();
//        var exception = Assertions.catchThrowable(()-> scheduleService.save(schedule));
//
//        assertThat(exception).isInstanceOf(InvalidScheduleDurationException.class)
//                .hasMessage("The duration is less than 1 minute");
//
//        Mockito.verify(scheduleRepository,Mockito.never()).save(schedule);
//    }
//
//    private Schedule buildValidSchedule(){
//        return Schedule.builder().duration(1L).isOpen(false).build();
//    }
}
