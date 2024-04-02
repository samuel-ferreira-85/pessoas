package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.entities.Pessoa;
import com.samuel.pessoas.repositories.EnderecoRepository;
import com.samuel.pessoas.repositories.PessoaRepository;
import com.samuel.pessoas.services.exceptions.ResourceNotFoundException;
import com.samuel.pessoas.tests.Factory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void listarEnderecosPorIdShouldReturnPageWhenIdExists() {
        Long existingId = 1L;
        when(enderecoRepository.findAll()).thenReturn(List.of(Factory.criarEndereco()));

        List<EnderecoDTO> result = enderecoService.listarEnderecosPorId(existingId);

        assertNotNull(result);
        verify(enderecoRepository).findAll();
    }

    @Test
    void findByIdShouldReturnPessoaDTOWhenIdExists() {
        Long existingId = 1L;
        when(enderecoRepository.findById(existingId)).thenReturn(Optional.of(Factory.criarEndereco()));

        EnderecoDTO result = enderecoService.findById(existingId);

        assertNotNull(result);
        verify(enderecoRepository).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingID() {
        Long nonExistingID = 1000L;
        when(enderecoRepository.findById(nonExistingID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.findById(nonExistingID);
        });
        verify(enderecoRepository).findById(nonExistingID);
    }

    @Test
    void saveShouldReturnEnderecoDTOWhenPessoaIdExists() {
        Long existingId = 1L;
        EnderecoDTO enderecoDTO = Factory.criarEnderecoDTO();
        Endereco endereco = Factory.criarEndereco();

        when(pessoaRepository.findById(existingId)).thenReturn(Optional.of(Factory.criarPessoa()));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        EnderecoDTO result = enderecoService.save(enderecoDTO);

        assertNotNull(result);
        assertEquals(enderecoDTO.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoDTO.getNumero(), result.getNumero());
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void saveShouldThrowResourceNotFoundExceptionWhenNonExistingPessoaId() {
        EnderecoDTO enderecoDTO = Factory.criarEnderecoDTO();
        when(pessoaRepository.findById(enderecoDTO.getPessoaId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.save(enderecoDTO);
        });
        verify(pessoaRepository).findById(enderecoDTO.getPessoaId());
    }

    @Test
    void atualizarShouldReturnEnderecoDTOWhenIdExists() {
        Long existingId = 1L;
        EnderecoDTO enderecoDTO = Factory.criarEnderecoDTO();
        Endereco endereco = Factory.criarEndereco();

        when(enderecoRepository.findById(existingId)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        EnderecoDTO result = enderecoService.atualizar(existingId, enderecoDTO);

        assertNotNull(result);
        assertEquals(enderecoDTO.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoDTO.getNumero(), result.getNumero());
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void atualizarShouldThrowResourceNotFoundExceptionWhenNonExistingId() {
        Long nonExistingID = 1000L;
        EnderecoDTO enderecoDTO = Factory.criarEnderecoDTO();
        when(enderecoRepository.findById(nonExistingID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.atualizar(nonExistingID, enderecoDTO);
        });
        verify(enderecoRepository).findById(nonExistingID);
    }

}