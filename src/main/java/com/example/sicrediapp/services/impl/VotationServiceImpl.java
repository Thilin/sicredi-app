package com.example.sicrediapp.services.impl;
import com.example.sicrediapp.api.dtos.VoteCountDTO;
import com.example.sicrediapp.api.dtos.VoteDTO;
import com.example.sicrediapp.api.exceptions.CountVoteSessionOpenException;
import com.example.sicrediapp.api.exceptions.DuplicateVoteSameSessionException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.api.exceptions.SessionClosedException;
import com.example.sicrediapp.model.entity.Votation;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.CheckCPFService;
import com.example.sicrediapp.services.VotationService;
import org.springframework.stereotype.Service;

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.*;

@Service
public class VotationServiceImpl implements VotationService {

    private VotationRepository votationRepository;
    private SessionRepository sessionRepository;
    private AssociateRepository associateRepository;
    private CheckCPFService checkCPFService;

    public VotationServiceImpl(VotationRepository votationRepository, SessionRepository sessionRepository
            , AssociateRepository associateRepository, CheckCPFService checkCPFService){
        this.votationRepository = votationRepository;
        this.sessionRepository = sessionRepository;
        this.associateRepository = associateRepository;
        this.checkCPFService = checkCPFService;
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

    @Override
    public VoteDTO vote(Long sessionId, boolean vote, Long associateId) {
        var dto = new VoteDTO();

        var session = sessionRepository.findById(sessionId).orElseThrow(()-> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        if(!session.isOpen())
            throw new SessionClosedException(SESSION_CLOSED.getDescription());
        var votation = votationRepository.findBySessionIdAndAssociateId(sessionId, associateId);
        if(votation != null)
            throw new DuplicateVoteSameSessionException(DUPLICATE_VOTE_SAME_SESSION.getDescription());
        else {
            var associate = associateRepository.findById(associateId).orElseThrow(()-> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
            checkCPFService.checkCPF(associate.getCpf());
            votation = new Votation();
            votation.setSession(session);
            votation.setAssociate(associate);
            votation.setVote(vote);
            votationRepository.save(votation);

            if(vote)
                dto.setVote("SIM");
            dto.setAssociateId(associate.getId());
            dto.setScheduleId(session.getSchedule().getId());
            dto.setSessionId(session.getId());
        }

        return dto;
    }
}
