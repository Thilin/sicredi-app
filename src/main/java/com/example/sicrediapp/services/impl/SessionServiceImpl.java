package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.api.dtos.SessionDTO;
import com.example.sicrediapp.api.dtos.SessionListDTO;
import com.example.sicrediapp.api.exceptions.InvalidSessionDurationException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.services.SessionService;
import com.example.sicrediapp.services.VotationService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    SessionRepository sessionRepository;
    ScheduleRepository scheduleRepository;
    VotationService votationService;

    public SessionServiceImpl(SessionRepository sessionRepository, ScheduleRepository scheduleRepository, VotationService votationService){
        this.sessionRepository = sessionRepository;
        this.scheduleRepository = scheduleRepository;
        this.votationService = votationService;
    }

    @Override
    public void save(SessionCreateDTO dto) {
        if(dto.getDuration() < 1)
            throw new InvalidSessionDurationException("A duração da sessão não pode ser menor que 1 minuto");

        var session = new Session();
        session.setDuration(dto.getDuration());
        session.setOpen(false);
        var schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new ObjectNotFoundException("Pauta não encontrada"));
        session.setSchedule(schedule);

        sessionRepository.save(session);
    }

    @Override
    public SessionDTO findById(Long id) {
        var dto = new SessionDTO();
        var session = sessionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Sessão não encontrada"));
        dto.setDuration(session.getDuration());
        dto.setOpen(session.isOpen());
        dto.setScheduleId(session.getSchedule().getId());
        return dto;
    }

    @Override
    public List<SessionListDTO> findAll() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream().map(session -> {
            var dto = new SessionListDTO();
            dto.setId(session.getId());
            dto.setDuration(session.getDuration());
            dto.setOpen(session.isOpen());
            dto.setScheduleId(session.getSchedule().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void openSession(Long id){
        var session = sessionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Sessão não encontrada"));
        session.setOpen(true);
        sessionRepository.save(session);
        finishSession(session);
    }

    private void finishSession(Session session){
        new Thread(() -> {
        int delay = (int) (1000*60*session.getDuration());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            session.setOpen(false);
            sessionRepository.save(session);
            var voteCount = votationService.countVotes(session.getId());
            System.out.println("Votos SIM: "+voteCount.getVotesYes());
            System.out.println("Votos NÃO: "+voteCount.getVotesNo());
        }).start();
    }
}
