package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Solicitante;
import org.labs.sistemabiblyjava.repository.SolicitanteRepository;
import org.labs.sistemabiblyjava.repository.fluentquery.SolicitantesFluentQuery;
import org.labs.sistemabiblyjava.repository.specification.SolicitanteSpecification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/solicitante")
@RestController
@AllArgsConstructor
public class SolicitanteRest {

    private SolicitanteRepository repository;
    

    @GetMapping
    public ResponseEntity<List<Solicitante>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/fq={first_name}")
    public ResponseEntity<List<Solicitante>> findAllByFirstName(@PathVariable String first_name){
        return ResponseEntity.ok(repository.findBy(SolicitanteSpecification.firstNameFirstLetter(first_name), SolicitantesFluentQuery.allSolicitantes()));
    }

    @PostMapping
    public ResponseEntity<Solicitante> insert(@RequestBody Solicitante resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitante> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitante> update(@PathVariable Long id,@RequestBody Solicitante resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
