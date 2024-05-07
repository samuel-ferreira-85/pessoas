package com.samuel.pessoas.dto;

import com.samuel.pessoas.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class EnderecoDTO {
    private Long id;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String cep;

    private String numero;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotNull
    private boolean principal;
    @NotNull
    private Long pessoaId;

    public EnderecoDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        cep = endereco.getCep();
        numero = endereco.getNumero();
        cidade = endereco.getCidade();
        estado = endereco.getEstado();
        principal = endereco.isPrincipal();
        pessoaId = endereco.getPessoaId();
    }
}
