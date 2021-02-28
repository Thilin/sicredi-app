package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create session", description = "Create a session. It will be created closed by default")
    public void create(@RequestBody @Valid SessionDTO dto){
        sessionService.save(dto);
    }
}
