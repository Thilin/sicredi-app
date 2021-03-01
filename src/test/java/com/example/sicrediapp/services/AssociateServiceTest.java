package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.impl.AssociateServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AssociateServiceTest {

    AssociateService associateService;

    @MockBean
    AssociateRepository associateRepository;
    @MockBean
    private VotationRepository votationRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @MockBean
    private CheckCPFService checkCPFService;

    @BeforeEach
    public void setUp(){
        this.associateService = new AssociateServiceImpl(associateRepository, votationRepository, sessionRepository, checkCPFService);
    }

    @Test
    @DisplayName("Should create a new associate")
    public void saveScheduleTest(){
        var associate = Associate.builder().name("Fulano").cpf("12345678900").build();
        var dto = AssociateDTO.builder().name("Fulano").cpf("12345678900").build();
        var savedAssociate = Associate.builder().id(1L).name("Fulano").cpf("12345678900").build();
        Mockito.when(associateRepository.save(associate))
                .thenReturn(savedAssociate);

        associateService.save(dto);

        assertThat(savedAssociate.getId()).isNotNull();
        assertThat(savedAssociate.getName()).isEqualTo("Fulano");
        assertThat(savedAssociate.getCpf()).isEqualTo("12345678900");
    }

    @Test
    @DisplayName("Should return an associate by Id")
    public void findAssociateByIdTest(){
        Long id = 1L;
        var associate = Associate.builder().id(id).name("Fulano").cpf("12345678900").build();

        Mockito.when(associateRepository.findById(id)).thenReturn(Optional.of(associate));

        var dto = associateService.findById(id);

        assertThat(dto.getName()).isEqualTo(associate.getName());
        assertThat(dto.getCpf()).isEqualTo(associate.getCpf());
    }

    @Test
    @DisplayName("Should not return a non-existing associate ")
    public void shouldNotFindAssociateByIdTest(){
        Long id = 1L;

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ObjectNotFoundException.class, () -> associateService.findById(id));
        String expectedMessage = "Associado não encontrado";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
}
