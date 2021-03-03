package com.example.sicrediapp.services;

import com.example.sicrediapp.services.impl.CheckCPFServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CheckCPFServiceTest {

    CheckCPFService checkCPFService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp(){
        this.checkCPFService = new CheckCPFServiceImpl();
    }

    @Test
    @DisplayName("Should return if associate is able or not to vote")
    public void shouldCheckCPF(){
        String cpf = "01322713200";
        String checkcpfurl = "https://user-info.herokuapp.com/users/";
        Mockito.when(restTemplate.getForEntity(checkcpfurl+cpf, String.class)).thenReturn(new ResponseEntity<>(cpf,HttpStatus.OK));
        ResponseEntity response = restTemplate.getForEntity(checkcpfurl+cpf, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Should return 404 Not Found: [ ] when the cpf is not a valid cpf")
    public void shouldReturnCPFNotFoundTest(){
        String cpf = "01322710";
//        String body = "ABLE_TO_VOTE";
        String checkcpfurl = "https://user-info.herokuapp.com/users/";

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(HttpClientErrorException.class, () -> checkCPFService.checkCPF(cpf));
        String expectedMessage = "404 Not Found: [ ]";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

}
