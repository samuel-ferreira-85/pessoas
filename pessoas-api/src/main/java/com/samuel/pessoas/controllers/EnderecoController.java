package com.samuel.pessoas.controllers;

import com.samuel.pessoas.dto.EnderecoDTO;
import com.samuel.pessoas.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;
    @GetMapping("/pessoa/{id}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.listarEnderecosPorId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> detalharEndereco(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> inserir(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO dto = enderecoService.save(enderecoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    ResponseEntity<EnderecoDTO> atualizar(@PathVariable Long id,  @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.atualizar(id, enderecoDTO));
    }
}
