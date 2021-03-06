package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateResponseDTO;
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

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.*;

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
    public AssociateResponseDTO save(AssociateDTO dto) {
        if(associateRepository.existsByCpf(dto.getCpf()))
            throw new DuplicateCPFException(DUPLICATE_CPF.getDescription());
        var associate = new Associate();
        associate.setName(dto.getName());
        associate.setCpf(dto.getCpf());
        associateRepository.save(associate);

        var responseDTO = new AssociateResponseDTO();
        responseDTO.setId(associate.getId());
        responseDTO.setCpf(associate.getCpf());
        responseDTO.setName(associate.getName());
        return responseDTO;
    }

    @Override
    public AssociateResponseDTO findById(Long id) {
        var associate = associateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        var dto = new AssociateResponseDTO();
        dto.setCpf(associate.getCpf());
        dto.setName(associate.getName());
        dto.setId(associate.getId());
        return dto;
    }

    @Override
    public List<AssociateResponseDTO> findAll() {
        List<Associate> associates = associateRepository.findAll();
        return associates.stream().map(associate -> {
            var dto = new AssociateResponseDTO();
            dto.setName(associate.getName());
            dto.setId(associate.getId());
            dto.setCpf(associate.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

}
