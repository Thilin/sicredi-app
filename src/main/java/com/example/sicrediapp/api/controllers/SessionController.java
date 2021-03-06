package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.*;
import com.example.sicrediapp.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {

    SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create session", description = "Create a session. It will be created closed by default")
    public ResponseEntity<Void> create(@RequestBody @Valid SessionCreateDTO dto){
        var responseDTO = sessionService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find session by id", description = "Find an session using its id")
    public ResponseEntity<SessionResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(sessionService.findById(id));
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "show all sessions", description = "Show all sessions informations")
    public ResponseEntity<List<SessionResponseDTO>> findAll(){
        return ResponseEntity.ok().body(sessionService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Open a session", description = "Open a Session giving its id")
    public ResponseEntity<SessionResponseDTO> openSession(@PathVariable Long id){
        return ResponseEntity.ok().body(sessionService.openSession(id));
    }
}
