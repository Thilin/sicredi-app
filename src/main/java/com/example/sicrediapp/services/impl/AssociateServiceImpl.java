package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.services.AssociateService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociateServiceImpl implements AssociateService {

    @Autowired
    AssociateRepository associateRepository;

    @Override
    public void save(AssociateDTO dto) {
        var associate = new Associate();
        associate.setName(dto.getName());
        associate.setCpf(dto.getCpf());
        associateRepository.save(associate);
    }

    @Override
    public AssociateDTO findById(Long id) {
        var associate = associateRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "associate"));
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
}
