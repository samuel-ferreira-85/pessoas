package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Pessoa;
import com.samuel.pessoas.repositories.PessoaRepository;
import com.samuel.pessoas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAllPaged(Pageable pageable) {
        Page<Pessoa> list = pessoaRepository.findAll(pageable);
        return list.map(PessoaDTO::new);
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        var pessoa = getPessoa(id);
        return new PessoaDTO(pessoa);
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        var pessoa = new Pessoa(pessoaDTO);
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }


    @Transactional
    public PessoaDTO update(PessoaDTO pessoaDTO, Long id) {
        Pessoa pessoa = getPessoa(id);
        BeanUtils.copyProperties(pessoaDTO, pessoa, "id", "enderecos");
        pessoa = pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }

    private Pessoa getPessoa(Long id) {
        var pessoa = pessoaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado para ID informado."));
        return pessoa;
    }

}
