package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateResponseDTO;
import com.example.sicrediapp.services.AssociateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<Void> create(@RequestBody @Valid AssociateDTO dto){
        var responseDTO = associateService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Find associate by id", description = "Find an associate using its id")
    public ResponseEntity<AssociateResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(associateService.findById(id));
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "show all associates", description = "Show all associates informations")
    public ResponseEntity<List<AssociateResponseDTO>> findAll(){
        return ResponseEntity.ok().body(associateService.findAll());
    }

}
