package com.samuel.pessoas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PessoaService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllShouldReturnPage() throws Exception {
        var page = new PageImpl<>(List.of(Factory.criarPessoaDTO()));
        when(productService.findAllPaged(any())).thenReturn(page);
        mockMvc.perform(get("/pessoas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void detalharShouldReturnProductWhenIdExists() throws Exception {
        Long existingId = 1L;
        when(productService.findById(existingId)).thenReturn(Factory.criarPessoaDTO());
        mockMvc.perform(get("/pessoas/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeCompleto").exists());
    }

    @Test
    void detalharShouldReturnNotFoundWhenIdDoesNoTExist() throws Exception {
        Long nonExistingID = 1000L;
        when(productService.findById(nonExistingID))
                .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/pessoas/{id}", nonExistingID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cadastrarShouldReturnPessoaDTOCreated() throws Exception {
        var pessoaDTO = Factory.criarPessoaDTO();
        String jsonBody = objectMapper.writeValueAsString(pessoaDTO);
        when(productService.save(any())).thenReturn(pessoaDTO);
        mockMvc.perform(post("/pessoas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nomeCompleto").exists());
    }

    @Test
    void updateShouldReturnProductDtoWhenIdExists() throws Exception {
        var pessoaDTO = Factory.criarPessoaDTO();
        String jsonBody = objectMapper.writeValueAsString(pessoaDTO);
        Long existingId = 1L;
        when(productService.update(any(), eq(existingId))).thenReturn(pessoaDTO);

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
        when(productService.update(any(), eq(nonExistingID))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/pessoas/{id}", nonExistingID)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}