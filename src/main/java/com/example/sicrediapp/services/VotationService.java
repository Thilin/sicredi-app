package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.VoteCountDTO;

public interface VotationService {
    VoteCountDTO countVotes(Long sessionId);

    void vote(Long sessionId, boolean vote, Long associateId);
}
