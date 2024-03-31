package com.samuel.pessoas.dto;

import com.samuel.pessoas.entities.Endereco;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private String estado;
    private boolean principal;
    private Long pessoaId;

    public EnderecoDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        cep = endereco.getCep();
        numero = endereco.getNumero();
        cidade = endereco.getCidade();
        estado = endereco.getCidade();
        principal = endereco.isPrincipal();
        pessoaId = endereco.getPessoaId();
    }
}
