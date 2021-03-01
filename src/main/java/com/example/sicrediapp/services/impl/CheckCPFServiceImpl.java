package com.example.sicrediapp.services.impl;
import com.example.sicrediapp.api.exceptions.UnableToVoteException;
import com.example.sicrediapp.services.CheckCPFService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckCPFServiceImpl implements CheckCPFService {
    @Override
    public void checkCPF(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        String checkcpfurl = "https://user-info.herokuapp.com/users/";
        ResponseEntity<String> response = restTemplate.getForEntity(checkcpfurl + cpf, String.class);
        if(response.getBody().contains("UNABLE_TO_VOTE"))
            throw new UnableToVoteException("Associado não pode votar");
        System.out.println(response.getBody());
    }
}
