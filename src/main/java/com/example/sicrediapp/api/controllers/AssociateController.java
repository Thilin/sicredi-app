package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.services.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
