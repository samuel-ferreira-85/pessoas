package com.samuel.pessoas.dto;

import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.entities.Pessoa;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private Set<Endereco> enderecos = new HashSet<>();

    public PessoaDTO(Pessoa pessoa) {
        id = pessoa.getId();
        nomeCompleto = pessoa.getNomeCompleto();
        dataNascimento = pessoa.getDataNascimento();
        enderecos = pessoa.getEnderecos();
    }
}
