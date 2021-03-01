package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.services.impl.SessionServiceImpl;
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
public class SessionServiceTest {

    SessionService sessionService;

    @MockBean
    SessionRepository sessionRepository;
    @MockBean
    private ScheduleRepository scheduleRepository;
    @MockBean
    private VotationService votationService;

    @BeforeEach
    public void setUp(){
        this.sessionService = new SessionServiceImpl(sessionRepository, scheduleRepository, votationService);
    }

    @Test
    @DisplayName("Should create a new associate")
    public void saveScheduleTest(){
        var schedule = Schedule.builder().description("Dividendos").build();
        var savedSchedule = Schedule.builder().id(1L).description("Dividendos").build();

        Mockito.when(scheduleRepository.save(schedule)).thenReturn(savedSchedule);
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        var session = Session.builder().isOpen(false).duration(1L).schedule(schedule).build();
        var dto = SessionCreateDTO.builder().duration(1L).scheduleId(1L).build();
        var savedSession = Session.builder().id(1L).isOpen(false).duration(1L).schedule(savedSchedule).build();

        Mockito.when(sessionRepository.save(session))
                .thenReturn(savedSession);

        sessionService.save(dto);

        assertThat(savedSession.getId()).isNotNull();
        assertThat(savedSession.getSchedule()).isEqualTo(savedSchedule);
        assertThat(savedSession.getDuration()).isGreaterThanOrEqualTo(1);
    }
}
