package com.samuel.pessoas.repositories;

import com.samuel.pessoas.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
