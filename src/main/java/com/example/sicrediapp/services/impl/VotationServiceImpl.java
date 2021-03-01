package com.example.sicrediapp.services.impl;
import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.exceptions.CountVoteSessionOpenException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.VotationService;
import org.springframework.stereotype.Service;

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
        var session = sessionRepository.findById(sessionId).orElseThrow(()-> new ObjectNotFoundException("Sessão não encontrada"));
        if(session.isOpen())
            throw new CountVoteSessionOpenException("Não é possível ter o resultado da votação durante uma sessão aberta");

        var dto = new VoteCountDTO();
        dto.setVotesYes(votationRepository.countBySessionIdAndVoteTrue(sessionId));
        dto.setVotesNo(votationRepository.countBySessionIdAndVoteFalse(sessionId));

        return dto;
    }
}
