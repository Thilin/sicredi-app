package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.api.exceptions.InvalidSessionDurationException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void save(SessionDTO dto) {
        if(dto.getDuration() < 1)
            throw new InvalidSessionDurationException("A duração da sessão não pode ser menor que 1 minuto");

        var session = new Session();
        session.setDuration(dto.getDuration());
        session.setOpen(dto.isOpen());
        var schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new ObjectNotFoundException("Pauta não encontrada"));
        session.setSchedule(schedule);

        sessionRepository.save(session);
    }
}
