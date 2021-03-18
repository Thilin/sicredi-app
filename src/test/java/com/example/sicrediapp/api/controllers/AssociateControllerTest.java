package com.example.sicrediapp.api.controllers;

import com.example.sicrediapp.api.dtos.AssociateDTO;
import com.example.sicrediapp.api.dtos.AssociateResponseDTO;
import com.example.sicrediapp.services.AssociateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(AssociateController.class)
@AutoConfigureMockMvc
public class AssociateControllerTest {

    String ASSOCIATE_API = "/associates";

    @InjectMocks
    AssociateController associateController;

    @Autowired
    MockMvc mvc;

    @MockBean
    private AssociateService associateService;

    @BeforeEach
    public void setUp(){
        this.associateController = new AssociateController(associateService);
    }

    @Test
    @DisplayName("Should create an Associate")
    public void createAssociateTest(){
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = AssociateResponseDTO.builder().id(1L).cpf("12345678900").build();
        var dto = AssociateDTO.builder().cpf("12345678900").build();
        Mockito.when(associateService.save(dto)).thenReturn(response);
        ResponseEntity<Void> responseEntity = associateController.create(dto);


        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    @DisplayName("Should get a Associate by id")
    public void getAssociateByIdTest() throws Exception {
        Long id = 1L;

        var dto = AssociateResponseDTO.builder().id(1L).cpf("12345678900").build();

        BDDMockito.given(associateService.findById(id)).willReturn(dto);

        var request = MockMvcRequestBuilders
                .get(ASSOCIATE_API+"/"+id)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("cpf").value(dto.getCpf()));
    }

    @Test
    @DisplayName("Should return Not Found when the associate does not exists")
    public void AssociateNotFoundTest(){
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        var response = AssociateResponseDTO.builder().id(1L).cpf("12345678900").build();
        var dto = AssociateDTO.builder().cpf("12345678900").build();
        Mockito.when(associateService.findById(1L)).thenReturn(null);

        ResponseEntity<AssociateResponseDTO> responseEntity = associateController.findById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should get all Associates")
    public void getAllAssociatesTest() throws Exception {

        List<AssociateResponseDTO> list= new ArrayList<>();
        var dto1 = AssociateResponseDTO.builder().id(1L).cpf("12345678900").build();
        var dto2 = AssociateResponseDTO.builder().id(1L).cpf("12345678900").build();
        list.add(dto1);
        list.add(dto2);

        BDDMockito.given(associateService.findAll()).willReturn(list);

        var request = MockMvcRequestBuilders
                .get(ASSOCIATE_API+"/all")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
