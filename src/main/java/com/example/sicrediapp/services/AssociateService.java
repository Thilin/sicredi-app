package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateResponseDTO;

import java.util.List;

public interface AssociateService {
    AssociateResponseDTO save(AssociateDTO dto);

    AssociateResponseDTO findById(Long id);

    List<AssociateResponseDTO> findAll();
}
