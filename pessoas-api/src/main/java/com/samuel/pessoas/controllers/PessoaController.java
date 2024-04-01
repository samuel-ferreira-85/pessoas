package com.samuel.pessoas.controllers;

import com.samuel.pessoas.dto.PessoaDTO;
import com.samuel.pessoas.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> listarTodos(Pageable pageable) {
        Page<PessoaDTO> pessoaDTOPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(pessoaDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> detalhar(@PathVariable Long id) {
        PessoaDTO pessoaDTO = service.findById(id);
        return ResponseEntity.ok(pessoaDTO);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> cadastrar(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoa = service.save(pessoaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id,
                                               @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoa = service.update(pessoaDTO, id);
        return ResponseEntity.ok(pessoa);
    }

}
