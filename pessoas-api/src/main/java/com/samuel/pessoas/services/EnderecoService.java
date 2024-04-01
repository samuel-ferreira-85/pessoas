package com.samuel.pessoas.services;

import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.repositories.EnderecoRepository;
import com.samuel.pessoas.repositories.PessoaRepository;
import com.samuel.pessoas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public List<EnderecoDTO> listarEnderecosPorId(Long id) {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                    .filter(e -> e.getPessoaId().equals(id))
                    .map(EnderecoDTO::new)
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnderecoDTO findById(Long id) {
        var endereco = getEndereco(id);
        return new EnderecoDTO(endereco);
    }

    @Transactional
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        var endereco = new Endereco(enderecoDTO);
        var pessoa = pessoaRepository.findById(enderecoDTO.getPessoaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));

        endereco.setPessoa(pessoa);
        enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    @Transactional
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) {
        var endereco = getEndereco(id);
        BeanUtils.copyProperties(enderecoDTO, endereco, "id");
        enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    private Endereco getEndereco(Long id) {
        var endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));
        return endereco;
    }
}
