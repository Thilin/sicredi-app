package com.example.sicrediapp.services.impl;
import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.exceptions.CountVoteSessionOpenException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.VotationService;
import org.springframework.stereotype.Service;

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.*;

@Service
public class VotationServiceImpl implements VotationService {

    private VotationRepository votationRepository;
    private SessionRepository sessionRepository;

    public VotationServiceImpl(VotationRepository votationRepository, SessionRepository sessionRepository){
        this.votationRepository = votationRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public VoteCountDTO countVotes(Long sessionId) {
        var session = sessionRepository.findById(sessionId).orElseThrow(()-> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        if(session.isOpen())
            throw new CountVoteSessionOpenException(COUNT_VOTE_SESSION_OPEN.getDescription());

        var dto = new VoteCountDTO();
        dto.setVotesYes(votationRepository.countBySessionIdAndVoteTrue(sessionId));
        dto.setVotesNo(votationRepository.countBySessionIdAndVoteFalse(sessionId));

        return dto;
    }
}
