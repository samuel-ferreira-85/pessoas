package com.samuel.pessoas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samuel.pessoas.dto.EnderecoDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private String estado;
    private boolean principal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Endereco(Long id, String logradouro, String cep, String numero, String cidade, String estado, Pessoa pessoa) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        principal = false;
        this.pessoa = pessoa;
    }

    public Endereco(EnderecoDTO enderecoDTO) {
        id = enderecoDTO.getId();
        logradouro = enderecoDTO.getLogradouro();
        cep = enderecoDTO.getCep();
        numero = enderecoDTO.getNumero();
        cidade = enderecoDTO.getCidade();
        estado = enderecoDTO.getEstado();
        principal = enderecoDTO.isPrincipal();
    }

    @JsonProperty("pessoaId")
    public Long getPessoaId() {
        return pessoa != null ? pessoa.getId() : null;
    }

}
