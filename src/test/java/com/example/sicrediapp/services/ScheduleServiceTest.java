package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleListDTO;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Associate;
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

import java.util.ArrayList;
import java.util.List;
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

    @Test
    @DisplayName("Should not return a non-existing schedule ")
    public void shouldNotFindAssociateByIdTest(){
        Long id = 1L;

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ObjectNotFoundException.class, () -> scheduleService.findById(id));
        String expectedMessage = "Pauta não encontrada.";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

    @Test
    @DisplayName("Should return a list of associates")
    public void shouldReturnAssociateListsTest(){

        var schedule1 = Schedule.builder().id(1L).description("Dividendos").build();
        var schedule2 = Schedule.builder().id(2L).description("Crédito").build();
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(schedule1);
        schedules.add(schedule2);

        Mockito.when(scheduleRepository.findAll()).thenReturn(schedules);

        List<ScheduleListDTO> dtos = scheduleService.findAll();
        assertThat(dtos.size()).isEqualTo(2);
    }
}
