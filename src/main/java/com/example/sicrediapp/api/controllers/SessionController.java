package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.*;
import com.example.sicrediapp.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create session", description = "Create a session. It will be created closed by default")
    public void create(@RequestBody @Valid SessionCreateDTO dto){
        sessionService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find session by id", description = "Find an session using its id")
    public SessionDTO findById(@PathVariable Long id){
        return sessionService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "show all sessions", description = "Show all sessions informations")
    public List<SessionListDTO> findAll(){
        return sessionService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Open a session", description = "Open a Session giving its id")
    public void openSession(@PathVariable Long id){
        sessionService.openSession(id);
    }
}
