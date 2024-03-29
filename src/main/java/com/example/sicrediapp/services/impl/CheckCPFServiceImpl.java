package com.example.sicrediapp.services.impl;

import com.example.sicrediapp.api.exceptions.ExternalServiceUnavailableException;
import com.example.sicrediapp.api.exceptions.UnableToVoteException;
import com.example.sicrediapp.services.CheckCPFService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.EXTERNAL_SERVICE_UNAVAILABLE;
import static com.example.sicrediapp.api.exceptions.ExceptionsEnum.UNABLE_TO_VOTE;

@Service
public class CheckCPFServiceImpl implements CheckCPFService {
    @Override
    public void checkCPF(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        String checkcpfurl = "https://user-info.herokuapp.com/users/";

        @NonNull
        ResponseEntity<String> response = restTemplate.getForEntity(checkcpfurl + cpf, String.class);
        if(response.getBody() == null)
            throw new ExternalServiceUnavailableException(EXTERNAL_SERVICE_UNAVAILABLE.getDescription());
        if(response.getBody().contains("UNABLE_TO_VOTE"))
            throw new UnableToVoteException(UNABLE_TO_VOTE.getDescription());
    }
}
