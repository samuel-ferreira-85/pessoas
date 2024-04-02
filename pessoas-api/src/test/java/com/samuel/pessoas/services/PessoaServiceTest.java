package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Pessoa;
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
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void findAllPagedShouldReturnPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        PageImpl<Pessoa> page = new PageImpl<>(List.of(Factory.criarPessoa()));
        when(pessoaRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Page<PessoaDTO> result = pessoaService.findAllPaged(pageable);

        assertNotNull(result);
        verify(pessoaRepository).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnPessoaDTOWhenIdExists() {
        Long existingId = 1L;
        when(pessoaRepository.findById(existingId)).thenReturn(Optional.of(Factory.criarPessoa()));

        PessoaDTO result = pessoaService.findById(existingId);

        assertNotNull(result);
        verify(pessoaRepository).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingID() {
        Long nonExistingID = 1000L;
        when(pessoaRepository.findById(nonExistingID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.findById(nonExistingID);
        });
        verify(pessoaRepository).findById(nonExistingID);
    }

    @Test
    void saveShouldReturnPessoaDTO() {
        PessoaDTO pessoaDTO = Factory.criarPessoaDTO();
        Pessoa pessoa = Factory.criarPessoa();

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        PessoaDTO result = pessoaService.save(pessoaDTO);

        assertNotNull(result);
        assertEquals(pessoaDTO.getNomeCompleto(), result.getNomeCompleto());
        assertEquals(pessoaDTO.getDataNascimento(), result.getDataNascimento());
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void updateShouldReturnPessoaDTOWhenIdExists() {
        Long existingId = 1L;
        var pessoa = Factory.criarPessoa();
        var pessoaDTO = Factory.criarPessoaDTO();
        when(pessoaRepository.findById(existingId)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(ArgumentMatchers.any())).thenReturn(pessoa);
        PessoaDTO result = pessoaService.update(pessoaDTO, existingId);

        assertNotNull(result);
        verify(pessoaRepository).findById(existingId);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenNonExistingID() {
        Long nonExistingID = 1000L;
        when(pessoaRepository.findById(nonExistingID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            pessoaService.update(Factory.criarPessoaDTO(), nonExistingID);
        });
        verify(pessoaRepository).findById(nonExistingID);
    }


}