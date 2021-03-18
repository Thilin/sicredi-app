package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.dtos.VoteDTO;
import com.example.sicrediapp.services.VotationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/votations")
@Slf4j
public class VotationController {

    VotationService votationService;

    public VotationController(VotationService votationService){
        this.votationService = votationService;
    }

    @GetMapping(value = "/{sessionId}/result", produces = "application/json")
    @Operation(summary = "Show votation result", description = "Find a votation by id and show the vote count")
    public ResponseEntity<VoteCountDTO> votationResult(@PathVariable Long sessionId){
        log.info("retrieving the vote result for session id: {}", sessionId);
        var dto = votationService.countVotes(sessionId);
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok().body(votationService.countVotes(sessionId));
        }
    }

    @PutMapping(value = "/session/{sessionId}", produces = "application/json")
    @Operation(summary = "Vote in a session", description = "Vote in an opened session")
    public ResponseEntity<VoteDTO> vote(@PathVariable Long sessionId, @RequestParam boolean vote, @RequestParam Long associateId) {
        log.info("Voting in session id: {}", sessionId, " vote: {}", vote, " withe the assosciate id: {}", associateId);
        var dto = votationService.vote(sessionId, vote, associateId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(dto);
        }
    }
}
