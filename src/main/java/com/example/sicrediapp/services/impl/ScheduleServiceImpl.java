package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.api.exceptions.InvalidScheduleDurationException;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.services.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        if(schedule.getDuration() < 1L)
            throw new InvalidScheduleDurationException("The duration is less than 1 minute");

        return scheduleRepository.save(schedule);
    }
}
