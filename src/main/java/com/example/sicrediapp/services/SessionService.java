package com.example.sicrediapp.services;
import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.api.dtos.SessionListDTO;

import java.util.List;

public interface SessionService {
    void save(SessionCreateDTO dto);

    SessionDTO findById(Long id);

    List<SessionListDTO> findAll();

    void openSession(Long id);
}
