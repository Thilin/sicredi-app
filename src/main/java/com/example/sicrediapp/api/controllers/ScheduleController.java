package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.services.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;
    private ModelMapper modelMapper;

    public ScheduleController(ScheduleService scheduleService, ModelMapper modelMapper){
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO create(@Valid @RequestBody ScheduleDTO dto){
        var schedule = modelMapper.map(dto, Schedule.class);
        return modelMapper.map(scheduleService.save(schedule), ScheduleDTO.class);
    }
}
