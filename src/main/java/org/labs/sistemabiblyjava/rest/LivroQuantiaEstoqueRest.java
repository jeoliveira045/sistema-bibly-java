package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.LivroQuantiaEstoque;
import org.labs.sistemabiblyjava.repository.LivroQuantiaEstoqueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/livro-quantia-estoque")
@RestController
@AllArgsConstructor
public class LivroQuantiaEstoqueRest {
    private LivroQuantiaEstoqueRepository repository;


    @GetMapping
    public ResponseEntity<List<LivroQuantiaEstoque>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<LivroQuantiaEstoque> insert(@RequestBody LivroQuantiaEstoque resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroQuantiaEstoque> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroQuantiaEstoque> update(@PathVariable Long id,@RequestBody LivroQuantiaEstoque resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
