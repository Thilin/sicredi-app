package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void save(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setDescription(dto.getDescription());
        scheduleRepository.save(schedule);
    }
}
