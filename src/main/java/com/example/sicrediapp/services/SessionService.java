package com.example.sicrediapp.services;
import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.api.dtos.SessionResponseDTO;

import java.util.List;

public interface SessionService {
    SessionResponseDTO save(SessionCreateDTO dto);

    SessionResponseDTO findById(Long id);

    List<SessionResponseDTO> findAll();

    SessionResponseDTO openSession(Long id);
}
