package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.dtos.ScheduleListDTO;
import com.example.sicrediapp.api.dtos.ScheduleResponseDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDTO save(ScheduleDTO dto);

    ScheduleDTO findById(Long id);

    List<ScheduleListDTO> findAll();
}
