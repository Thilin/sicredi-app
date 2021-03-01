package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateListDTO;
import com.example.sicrediapp.api.exceptions.DuplicateCPFException;
import com.example.sicrediapp.api.exceptions.ObjectNotFoundException;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.entity.Session;
import com.example.sicrediapp.model.entity.Votation;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import com.example.sicrediapp.model.repositories.SessionRepository;
import com.example.sicrediapp.model.repositories.VotationRepository;
import com.example.sicrediapp.services.impl.AssociateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    @DisplayName("Should return a list of associates")
    public void shouldReturnAssociateListsTest(){

        var associate1 = Associate.builder().id(1L).name("Fulano").cpf("12345678900").build();
        var associate2 = Associate.builder().id(2L).name("Beltrano").cpf("12345678901").build();
        List<Associate> associates = new ArrayList<>();
        associates.add(associate1);
        associates.add(associate2);

        Mockito.when(associateRepository.findAll()).thenReturn(associates);

        List<AssociateListDTO> dtos = associateService.findAll();
        assertThat(dtos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return an exception when try to save an associate with duplicated CPF")
    public void shouldNotBeAbleToSaveAssociateWithDuplicatedCPFTest() {
        var associate = Associate.builder().name("Fulano").cpf("12345678900").build();
        var dto = AssociateDTO.builder().name("Fulano").cpf("12345678900").build();
        var savedAssociate = Associate.builder().id(1L).name("Fulano").cpf("12345678900").build();
        Mockito.when(associateRepository.existsByCpf(Mockito.anyString())).thenReturn(true);

        Throwable exception = org.assertj.core.api.Assertions.catchThrowable(() -> associateService.save(dto));

        assertThat(exception).isInstanceOf(DuplicateCPFException.class).hasMessage("O CPF já existe na base de dados");

        Mockito.verify(associateRepository, Mockito.never()).save(associate);
    }

    @Test
    @DisplayName("Should vote with sucess")
    public void shouldVoteWithSuccessTest(){
        boolean vote = false;
        var session = Session.builder().id(1L).isOpen(true).build();
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        var associate = Associate.builder().id(1L).build();
        Mockito.when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));

        var votation = Votation.builder().associate(associate).vote(vote).session(session).build();
        var savedVotation = Votation.builder().id(1L).associate(associate).vote(vote).session(session).build();

        Mockito.when(votationRepository.save(votation)).thenReturn(savedVotation);

        associateService.vote(session.getId(), vote, associate.getId());

        assertThat(savedVotation.getId()).isNotNull();
        assertThat(session.isOpen()).isTrue();
        assertThat(savedVotation.getVote()).isNotNull();
    }
}
