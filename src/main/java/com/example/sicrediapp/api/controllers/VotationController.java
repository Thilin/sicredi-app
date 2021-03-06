package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.dtos.VoteDTO;
import com.example.sicrediapp.services.VotationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/votations")
public class VotationController {

    @Autowired
    VotationService votationService;

    @GetMapping(value = "/{sessionId}/result", produces = "application/json")
    @Operation(summary = "Show votation result", description = "Find a votation by id and show the vote count")
    public ResponseEntity<VoteCountDTO> findById(@PathVariable Long sessionId){
        return ResponseEntity.ok().body(votationService.countVotes(sessionId));
    }

    @PutMapping(value = "/session/{sessionId}", produces = "application/json")
    @Operation(summary = "Vote in a session", description = "Vote in an opened session")
    public ResponseEntity<VoteDTO> vote(@PathVariable Long sessionId, @RequestParam boolean vote, @RequestParam Long associateId){
        return ResponseEntity.ok().body(votationService.vote(sessionId, vote, associateId));
    }
}
