package com.samuel.pessoas.dto;

import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.entities.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotBlank
    private String nomeCompleto;
    @NotNull
    @Past
    private LocalDate dataNascimento;
    private Set<Endereco> enderecos = new HashSet<>();

    public PessoaDTO(Pessoa pessoa) {
        id = pessoa.getId();
        nomeCompleto = pessoa.getNomeCompleto();
        dataNascimento = pessoa.getDataNascimento();
        enderecos = pessoa.getEnderecos();
    }
}
