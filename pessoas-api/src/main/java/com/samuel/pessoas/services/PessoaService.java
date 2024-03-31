package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Pessoa;
import com.samuel.pessoas.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        return pessoaRepository.findAll().stream()
                .map(PessoaDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAllPaged(Pageable pageable) {
        Page<Pessoa> list = pessoaRepository.findAll(pageable);
        return list.map(PessoaDTO::new);
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        return pessoaOptional.map(PessoaDTO::new).orElse(null);
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        var pessoa = new Pessoa(pessoaDTO);
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }


    @Transactional
    public PessoaDTO update(PessoaDTO pessoaDTO, Long id) {
        Pessoa pessoa = pessoaRepository.getReferenceById(id);
        BeanUtils.copyProperties(pessoaDTO, pessoa, "id", "enderecos");
        return new PessoaDTO(pessoa);
    }
}
