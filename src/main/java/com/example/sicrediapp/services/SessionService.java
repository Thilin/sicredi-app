package com.example.sicrediapp.services;
import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.api.dtos.SessionListDTO;

import java.util.List;

public interface SessionService {
    void save(SessionDTO dto);

    SessionDTO findById(Long id);

    List<SessionListDTO> findAll();
}
