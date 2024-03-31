package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.entities.Pessoa;
import com.samuel.pessoas.repositories.EnderecoRepository;
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
    @Autowired
    private EnderecoRepository enderecoRepository;

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

    public List<EnderecoDTO> findAllEnderecos(Long id) {
        Pessoa pessoa = pessoaRepository.getReferenceById(id);
        return pessoa.getEnderecos().stream().map(EnderecoDTO::new).toList();
    }

    public EnderecoDTO findEndereco(Long id, Long idEndereco) {
        Pessoa pessoa = pessoaRepository.getReferenceById(id);
        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId() == idEndereco)
                .findFirst()
                .orElse(null);
        if (endereco == null) return null;
        return new EnderecoDTO(endereco);
    }

    @Transactional
    public PessoaDTO inserirEndereco(Long id, EnderecoDTO enderecoDTO) {
        var pessoa = pessoaRepository.getReferenceById(id);
        var endereco = new Endereco(enderecoDTO);
        endereco.setPessoa(pessoa);
        endereco = enderecoRepository.save(endereco);
        if (endereco.isPrincipal()) {
            pessoa.getEnderecos().add(endereco);
            pessoa.setEnderecoPrincipal(endereco);
        }
        pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }

    @Transactional
    public PessoaDTO atualizarEndereco(Long id, Long idEndereco, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaRepository.getReferenceById(id);

        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId() == idEndereco)
                .findFirst()
                .orElse(null);
        if (endereco == null) return null;

        var enderecoUpdate = new Endereco(enderecoDTO);
        BeanUtils.copyProperties(enderecoUpdate, endereco, "id");
        endereco.setPessoa(pessoa);
        enderecoRepository.save(endereco);
        pessoa.getEnderecos().add(endereco);
        pessoaRepository.save(pessoa);

        return new PessoaDTO(pessoa);
    }
}
