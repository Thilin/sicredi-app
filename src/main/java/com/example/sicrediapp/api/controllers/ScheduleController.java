package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.services.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Register a schedule", description = "Register a schedule to be voted")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ScheduleDTO dto){
        scheduleService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find schedule by id", description = "Find an schedule using its id")
    public ScheduleDTO findById(@PathVariable Long id){
        return scheduleService.findById(id);
    }
}
