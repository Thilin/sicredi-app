package com.example.sicrediapp.controllers;

import com.example.sicrediapp.domains.Schedule;
import com.example.sicrediapp.dtos.ScheduleDTO;
import com.example.sicrediapp.services.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;
    private ModelMapper modelMapper;

    public ScheduleController(ScheduleService scheduleService, ModelMapper modelMapper){
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO create(@RequestBody ScheduleDTO dto){
        var schedule = modelMapper.map(dto, Schedule.class);
        return modelMapper.map(scheduleService.save(schedule), ScheduleDTO.class);
    }
}
