package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.ScheduleDTO;

public interface ScheduleService {
    void save(ScheduleDTO dto);

    ScheduleDTO findById(Long id);
}
