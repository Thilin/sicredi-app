package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDTO save(ScheduleDTO dto);

    ScheduleResponseDTO findById(Long id);

    List<ScheduleResponseDTO> findAll();
}
