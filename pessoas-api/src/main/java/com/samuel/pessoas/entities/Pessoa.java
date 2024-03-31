package com.samuel.pessoas.entities;


import com.samuel.pessoas.dto.PessoaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "pessoa")
    private Set<Endereco> enderecos = new HashSet<>();

    public Pessoa(PessoaDTO dto) {
        this.id = dto.getId();
        this.nomeCompleto = dto.getNomeCompleto();
        this.dataNascimento = dto.getDataNascimento();
        this.enderecos = dto.getEnderecos();
    }

    public Endereco getEnderecoPrincipal() {
        return enderecos.stream()
                .filter(Endereco::isPrincipal)
                .findFirst()
                .orElse(null);
    }

    public void setEnderecoPrincipal(Endereco endereco) {
        if (endereco != null) {
            enderecos.forEach(e -> e.setPrincipal(false));
            endereco.setPrincipal(true);
        }
    }

}
