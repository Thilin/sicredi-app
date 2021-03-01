package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;

import java.util.List;

public interface AssociateService {
    void save(AssociateDTO dto);

    AssociateDTO findById(Long id);

    List<AssociateListDTO> findAll();

    void vote(Long sessionId, boolean vote, Long associateId);
}
