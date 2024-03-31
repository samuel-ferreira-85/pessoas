package com.samuel.pessoas.repositories;

import com.samuel.pessoas.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
