package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.services.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/associates")
public class AssociateController {

    private AssociateService associateService;

    public AssociateController(AssociateService associateService){
        this.associateService = associateService;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Register an associate", description = "Register an associate to vote in a schedule")
    public void create(@RequestBody @Valid AssociateDTO dto){
        associateService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find associate by id", description = "Find an associate using its id")
    public AssociateDTO findById(@PathVariable Long id){
        return associateService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "show all associates", description = "Show all associates informations")
    public List<AssociateListDTO> findAll(){
        return associateService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/session/{sessionId}", produces = "application/json")
    @Operation(summary = "Vote in a session", description = "Vote in an opened session")
    public void openSession(@PathVariable Long sessionId, @RequestParam boolean vote, @RequestParam Long associateId){
        associateService.vote(sessionId, vote, associateId);
    }
}
