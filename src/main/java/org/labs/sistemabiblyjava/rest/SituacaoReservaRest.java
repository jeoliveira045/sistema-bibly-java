package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.SituacaoReserva;
import org.labs.sistemabiblyjava.repository.SituacaoReservaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/situacao-solicitacao")
@RestController
@AllArgsConstructor
public class SituacaoReservaRest {
    private SituacaoReservaRepository repository;
//    private final SituacaoSolicitacaoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<SituacaoReserva>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<SituacaoReserva> insert(@RequestBody SituacaoReserva resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoReserva> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoReserva> update(@PathVariable Long id, @RequestBody SituacaoReserva resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
