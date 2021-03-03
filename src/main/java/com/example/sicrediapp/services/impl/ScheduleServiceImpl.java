package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleListDTO;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.services.ScheduleService;
import com.example.sicrediapp.services.utils.ExceptionsEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sicrediapp.services.utils.ExceptionsEnum.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }
    @Override
    public void save(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setDescription(dto.getDescription());
        scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleDTO findById(Long id) {
        var schedule = scheduleRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(SCHEDULE_NOT_FOUND.getDescription()));
        var dto = new ScheduleDTO();
        dto.setDescription(schedule.getDescription());
        return dto;
    }

    @Override
    public List<ScheduleListDTO> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(schedule -> {
            var dto = new ScheduleListDTO();
            dto.setId(schedule.getId());
            dto.setDescription(schedule.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }
}
