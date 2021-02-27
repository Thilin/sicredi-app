package com.example.sicrediapp.controllers;

import com.example.sicrediapp.domains.Schedule;
import com.example.sicrediapp.dtos.ScheduleDTO;
import com.example.sicrediapp.exceptions.ApiErrors;
import com.example.sicrediapp.exceptions.InvalidScheduleDurationException;
import com.example.sicrediapp.services.ScheduleService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException ex){
        BindingResult bindResult = ex.getBindingResult();
        return new ApiErrors(bindResult);
    }

    @ExceptionHandler(InvalidScheduleDurationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleInvalidScheduleDurationException(InvalidScheduleDurationException ex){
        return new ApiErrors(ex);
    }
}
