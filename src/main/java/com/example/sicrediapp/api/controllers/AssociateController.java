package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.services.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "associates")
public class AssociateController {

    @Autowired
    AssociateService associateService;

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

}
