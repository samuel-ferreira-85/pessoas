package com.samuel.pessoas.tests;

import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.entities.Endereco;
import com.samuel.pessoas.entities.Pessoa;

import java.time.LocalDate;

public class Factory {

    public static Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Samuel Ferreira");
        pessoa.setDataNascimento(LocalDate.of(1985, 8, 27));
        return pessoa;
    }

    public static PessoaDTO criarPessoaDTO() {
        Pessoa pessoa = criarPessoa();
        return new PessoaDTO(pessoa);
    }

    public static Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setLogradouro("Rua A-1");
        endereco.setNumero("200-B");
        endereco.setCep("62840-000");
        endereco.setCidade("Beberibe");
        endereco.setEstado("Ceará");
        endereco.setPessoa(criarPessoa());

        return endereco;
    }

    public static EnderecoDTO criarEnderecoDTO() {
        Endereco endereco = criarEndereco();
        return new EnderecoDTO(endereco);
    }

}
