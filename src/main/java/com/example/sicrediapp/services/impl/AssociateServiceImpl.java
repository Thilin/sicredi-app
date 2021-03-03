package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.api.exceptions.DuplicateCPFException;
import com.example.sicrediapp.api.exceptions.DuplicateVoteSameSessionException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.api.exceptions.SessionClosedException;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.entity.Votation;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.AssociateService;
import com.example.sicrediapp.services.CheckCPFService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sicrediapp.services.utils.ExceptionsEnum.*;

@Service
public class AssociateServiceImpl implements AssociateService {


    private AssociateRepository associateRepository;
    private VotationRepository votationRepository;
    private SessionRepository sessionRepository;
    private CheckCPFService checkCPFService;

    public AssociateServiceImpl(AssociateRepository associateRepository, VotationRepository votationRepository,
                                SessionRepository sessionRepository, CheckCPFService checkCPFService){
        this.associateRepository = associateRepository;
        this.votationRepository = votationRepository;
        this.sessionRepository = sessionRepository;
        this.checkCPFService = checkCPFService;
    }

    @Override
    public void save(AssociateDTO dto) {
        if(associateRepository.existsByCpf(dto.getCpf()))
            throw new DuplicateCPFException(DUPLICATE_CPF.getDescription());
        var associate = new Associate();
        associate.setName(dto.getName());
        associate.setCpf(dto.getCpf());
        associateRepository.save(associate);
    }

    @Override
    public AssociateDTO findById(Long id) {
        var associate = associateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(ASSOCIATE_NOT_FOUND.getDescription()));
        var dto = new AssociateDTO();
        dto.setCpf(associate.getCpf());
        dto.setName(associate.getName());
        return dto;
    }

    @Override
    public List<AssociateListDTO> findAll() {
        List<Associate> associates = associateRepository.findAll();
        return associates.stream().map(associate -> {
            var dto = new AssociateListDTO();
            dto.setName(associate.getName());
            dto.setId(associate.getId());
            dto.setCpf(associate.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void vote(Long sessionId, boolean vote, Long associateId) {

        var session = sessionRepository.findById(sessionId).orElseThrow(()-> new ObjectNotFoundException(SESSION_NOT_FOUND.getDescription()));
        if(!session.isOpen())
            throw new SessionClosedException(SESSION_CLOSED.getDescription());
        var votation = votationRepository.findBySessionIdAndAssociateId(sessionId, associateId);
        if(votation != null)
            throw new DuplicateVoteSameSessionException(DUPLICATE_VOTE_SAME_SESSION.getDescription());
        else {
            var associate = associateRepository.findById(associateId).orElseThrow(()-> new ObjectNotFoundException(ASSOCIATE_NOT_FOUND.getDescription()));
            checkCPFService.checkCPF(associate.getCpf());
            votation = new Votation();
            votation.setSession(session);
            votation.setAssociate(associate);
            votation.setVote(vote);
            votationRepository.save(votation);
        }
    }
}
