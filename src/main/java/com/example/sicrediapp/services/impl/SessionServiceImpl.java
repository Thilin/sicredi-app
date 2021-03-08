package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.dtos.SessionCreateDTO;
import com.example.sicrediapp.api.dtos.SessionResponseDTO;
import com.example.sicrediapp.api.exceptions.InvalidSessionDurationException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.repositories.ScheduleRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.services.SessionService;
import com.example.sicrediapp.services.VotationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.*;

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
    public SessionResponseDTO save(SessionCreateDTO dto) {
        if(dto.getDuration() < 1)
            throw new InvalidSessionDurationException(INVALID_SESSION_DURATION.getDescription());

        var session = new Session();
        session.setDuration(dto.getDuration());
        session.setOpen(false);
        var schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        session.setSchedule(schedule);

        sessionRepository.save(session);
        var responseDTO = new SessionResponseDTO();
        responseDTO.setId(session.getId());
        responseDTO.setOpen(session.isOpen());
        responseDTO.setDuration(session.getDuration());
        responseDTO.setScheduleId(session.getSchedule().getId());

        return responseDTO;
    }

    @Override
    public SessionResponseDTO findById(Long id) {
        var dto = new SessionResponseDTO();
        var session = sessionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        dto.setDuration(session.getDuration());
        dto.setOpen(session.isOpen());
        dto.setScheduleId(session.getSchedule().getId());
        dto.setId(session.getId());
        return dto;
    }

    @Override
    public List<SessionResponseDTO> findAll() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream().map(session -> {
            var dto = new SessionResponseDTO();
            dto.setId(session.getId());
            dto.setDuration(session.getDuration());
            dto.setOpen(session.isOpen());
            dto.setScheduleId(session.getSchedule().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public SessionResponseDTO openSession(Long id){
        var session = sessionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(RESOURCE_NOT_FOUND.getDescription()));
        session.setOpen(true);
        sessionRepository.save(session);
        System.out.println("Sessão com id: " + session.getId()+ " abriu para votação.\n");

        var dto = new SessionResponseDTO();
        dto.setId(session.getId());
        dto.setOpen(session.isOpen());
        dto.setDuration(session.getDuration());
        dto.setScheduleId(session.getSchedule().getId());

        finishSession(session);

        return dto;
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
            var votesYes= voteCount.getVotesYes();
            var votesNo = voteCount.getVotesNo();

            var dispatcher = new KafkaDispatcherServiceImpl();
            var value = "Sessão com id: " + session.getId()+ " encerrou.\n" +
                    "Resultado:\n" +
                    "Votos SIM: " + votesYes +"\n" +
                    "Votos NÃO: " + votesNo + "\n";

            dispatcher.send("SESSION_CLOSED", value, value);

        }).start();
    }
}
