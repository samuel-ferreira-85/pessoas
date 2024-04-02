package com.samuel.pessoas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.services.PessoaService;
import com.samuel.pessoas.services.exceptions.ResourceNotFoundException;
import com.samuel.pessoas.tests.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllPagedShouldReturnPage() throws Exception {
        var page = new PageImpl<>(List.of(Factory.criarPessoaDTO()));
        when(pessoaService.findAllPaged(any())).thenReturn(page);
        mockMvc.perform(get("/pessoas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void detalharShouldReturnProductWhenIdExists() throws Exception {
        Long existingId = 1L;
        when(pessoaService.findById(existingId)).thenReturn(Factory.criarPessoaDTO());
        mockMvc.perform(get("/pessoas/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeCompleto").exists());
    }

    @Test
    void detalharShouldReturnNotFoundWhenIdDoesNoTExist() throws Exception {
        Long nonExistingID = 1000L;
        when(pessoaService.findById(nonExistingID))
                .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/pessoas/{id}", nonExistingID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cadastrarShouldReturnPessoaDTOCreated() throws Exception {
        var pessoaDTO = Factory.criarPessoaDTO();
        String jsonBody = objectMapper.writeValueAsString(pessoaDTO);
        when(pessoaService.save(any())).thenReturn(pessoaDTO);
        mockMvc.perform(post("/pessoas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeCompleto").exists());
    }

    @Test
    void cadastararShouldReturn400WhenBlankNomeCompleto() throws Exception {
        var dto = PessoaDTO.builder()
                .id(null)
                .nomeCompleto("")
                .dataNascimento(LocalDate.of(2004, 6, 25))
                .build();

        String jsonBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/pessoas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("nomeCompleto"))
                .andExpect(jsonPath("$[0].message").value("must not be blank"));
    }

    @Test
    void cadastararShouldReturn400WhenDataNascimentoPastDate() throws Exception {
        var dto = PessoaDTO.builder()
                .id(null)
                .nomeCompleto("Samuel Ferreira")
                .dataNascimento(LocalDate.of(2024, 4, 2))
                .build();

        String jsonBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/pessoas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("dataNascimento"))
                .andExpect(jsonPath("$[0].message").value("must be a past date"));
    }

    @Test
    void cadastararShouldReturn400WhenDataNascimentoIsNull() throws Exception {
        var dto = PessoaDTO.builder()
                .id(null)
                .nomeCompleto("Samuel Ferreira")
                .dataNascimento(null)
                .build();

        String jsonBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/pessoas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("dataNascimento"))
                .andExpect(jsonPath("$[0].message").value("must not be null"));
    }

    @Test
    void updateShouldReturnPessoaDtoWhenIdExists() throws Exception {
        var pessoaDTO = Factory.criarPessoaDTO();
        String jsonBody = objectMapper.writeValueAsString(pessoaDTO);
        Long existingId = 1L;
        when(pessoaService.update(any(), eq(existingId))).thenReturn(pessoaDTO);

        mockMvc.perform(put("/pessoas/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeCompleto").exists());
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        var pessoaDTO = Factory.criarPessoaDTO();
        String jsonBody = objectMapper.writeValueAsString(pessoaDTO);
        Long nonExistingID = 1000L;
        when(pessoaService.update(any(), eq(nonExistingID))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/pessoas/{id}", nonExistingID)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}