package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.SituacaoEmprestimo;
import org.labs.sistemabiblyjava.repository.SituacaoEmprestimoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/situacao-emprestimo")
@RestController
@AllArgsConstructor
public class SituacaoEmprestimoRest {
    private SituacaoEmprestimoRepository repository;
//    private final SituacaoEmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<SituacaoEmprestimo>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<SituacaoEmprestimo> insert(@RequestBody SituacaoEmprestimo resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoEmprestimo> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoEmprestimo> update(@PathVariable Long id,@RequestBody SituacaoEmprestimo resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
