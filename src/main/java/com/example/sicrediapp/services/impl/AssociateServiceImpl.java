package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    AssociateRepository associateRepository;
    @Autowired
    VotationRepository votationRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    CheckCPFService checkCPFService;

    @Override
    public void save(AssociateDTO dto) {
        var associate = new Associate();
        associate.setName(dto.getName());
        associate.setCpf(dto.getCpf());
        associateRepository.save(associate);
    }

    @Override
    public AssociateDTO findById(Long id) {
        var associate = associateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Associado não encontrado"));
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

        var session = sessionRepository.findById(sessionId).orElseThrow(()-> new ObjectNotFoundException("Sessão não encontrada"));
        if(!session.isOpen())
            throw new SessionClosedException("Não é possível votar em uma sessão fechada");
        var votation = votationRepository.findBySessionIdAndAssociateId(sessionId, associateId);
        if(votation != null)
            throw new DuplicateVoteSameSessionException("Um associado não pode votar mais de uma vez numa mesma sessão");
        else {
            var associate = associateRepository.findById(associateId).orElseThrow(()-> new ObjectNotFoundException("Associado não encontrado"));
            checkCPFService.checkCPF(associate.getCpf());
            votation = new Votation();
            votation.setSession(session);
            votation.setAssociate(associate);
            votation.setVote(vote);
            votationRepository.save(votation);
        }
    }
}
