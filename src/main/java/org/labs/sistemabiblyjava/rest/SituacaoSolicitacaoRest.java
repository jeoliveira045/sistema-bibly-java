package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;
import org.labs.sistemabiblyjava.repository.SituacaoSolicitacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/situacao-solicitacao")
@RestController
@AllArgsConstructor
public class SituacaoSolicitacaoRest {
    private SituacaoSolicitacaoRepository repository;
//    private final SituacaoSolicitacaoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<SituacaoSolicitacao>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<SituacaoSolicitacao> insert(@RequestBody SituacaoSolicitacao resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoSolicitacao> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoSolicitacao> update(@PathVariable Long id,@RequestBody SituacaoSolicitacao resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
