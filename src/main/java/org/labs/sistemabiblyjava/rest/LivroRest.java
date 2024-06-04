package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Livro;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/livro")
@RestController
@AllArgsConstructor
public class LivroRest {
    private LivroRepository repository;


    @GetMapping
    public ResponseEntity<List<Livro>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Livro> insert(@RequestBody Livro resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id,@RequestBody Livro resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
