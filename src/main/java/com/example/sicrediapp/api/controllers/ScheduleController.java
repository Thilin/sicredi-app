package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleListDTO;
import com.example.sicrediapp.services.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Register a schedule", description = "Register a schedule to be voted")
    public ResponseEntity<Void> create(@Valid @RequestBody ScheduleDTO dto){
        var responseDTO = scheduleService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find schedule by id", description = "Find an schedule using its id")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable Long id){
        if(scheduleService.findById(id) != null)
        return ResponseEntity.ok().body(scheduleService.findById(id));
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "show all schedules", description = "Show all schedules informations")
    public ResponseEntity<List<ScheduleListDTO>> findAll(){
        return ResponseEntity.ok().body(scheduleService.findAll());
    }
}
