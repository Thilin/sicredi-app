package com.example.sicrediapp.services;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.ScheduleDTO;
import com.example.sicrediapp.model.entity.Associate;
import com.example.sicrediapp.model.entity.Schedule;
import com.example.sicrediapp.model.repositories.AssociateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AssocieteServiceTest {

    @MockBean
    AssociateService associateService;

    @MockBean
    AssociateRepository associateRepository;

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
}
