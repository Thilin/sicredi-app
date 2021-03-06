package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.dtos.VoteDTO;

public interface VotationService {
    VoteCountDTO countVotes(Long sessionId);

    VoteDTO vote(Long sessionId, boolean vote, Long associateId);
}
