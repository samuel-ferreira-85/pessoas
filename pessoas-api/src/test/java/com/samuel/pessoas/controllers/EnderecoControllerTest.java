package com.samuel.pessoas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.services.EnderecoService;
import com.samuel.pessoas.services.exceptions.ResourceNotFoundException;
import com.samuel.pessoas.tests.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarEnderecosShouldReturnAListOfEnderecoDTOOfAPessoaWhenIdExists() throws Exception {
        Long existingId = 1L;
        var enderecoDTO = Factory.criarEnderecoDTO();
        when(enderecoService.listarEnderecosPorId(existingId))
                .thenReturn(List.of(enderecoDTO));
        mockMvc.perform(get("/enderecos/pessoa/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(enderecoDTO.getId()))
                .andExpect(jsonPath("$[0].logradouro").value(enderecoDTO.getLogradouro()));
    }

    @Test
    void listarEnderecosShouldReturnNotFoundWhenIdDoesNoTExist() throws Exception {
        Long nonExistingID = 1000L;
        when(enderecoService.listarEnderecosPorId(nonExistingID))
                .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/enderecos/pessoa/{id}", nonExistingID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void detalharEnderecoShouldReturnEnderecoDTOWhenIdExists() throws Exception {
        Long existingId = 1L;
        var enderecoDTO = Factory.criarEnderecoDTO();
        when(enderecoService.findById(existingId))
                .thenReturn(enderecoDTO);
        mockMvc.perform(get("/enderecos/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(enderecoDTO.getId()))
                .andExpect(jsonPath("$.logradouro").value(enderecoDTO.getLogradouro()));
    }

    @Test
    void detalharEnderecoShouldReturnNotFoundWhenIdDoesNoTExist() throws Exception {
        Long nonExistingID = 1000L;
        when(enderecoService.findById(nonExistingID))
                .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/enderecos/{id}", nonExistingID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void inserirShouldReturnEnderecoDTOCreated() throws Exception {
        var enderecoDTO = Factory.criarEnderecoDTO();
        String jsonBody = objectMapper.writeValueAsString(enderecoDTO);
        when(enderecoService.save(any())).thenReturn(enderecoDTO);
        mockMvc.perform(post("/enderecos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.logradouro").exists());
    }

    @Test
    void atualizarShouldReturnPessoaDTOWhenIdExists() throws Exception {
        var enderecoDTO = Factory.criarEnderecoDTO();
        String jsonBody = objectMapper.writeValueAsString(enderecoDTO);
        Long existingId = 1L;
        when(enderecoService.atualizar(eq(existingId), any(EnderecoDTO.class)))
                .thenReturn(enderecoDTO);

        mockMvc.perform(put("/enderecos/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.logradouro").exists());
    }

    @Test
    void atualizarShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        var enderecoDTO = Factory.criarEnderecoDTO();
        String jsonBody = objectMapper.writeValueAsString(enderecoDTO);
        Long nonExistingID = 1000L;
        when(enderecoService.atualizar(eq(nonExistingID), any(EnderecoDTO.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/enderecos/{id}", nonExistingID)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}