package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create session", description = "Create a session. It will be created closed by default")
    public void create(@RequestBody @Valid SessionDTO dto){
        sessionService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find session by id", description = "Find an session using its id")
    public SessionDTO findById(@PathVariable Long id){
        return sessionService.findById(id);
    }
}
