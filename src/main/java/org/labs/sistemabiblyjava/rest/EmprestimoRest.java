package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.service.RealizarEmprestimoService;
import org.labs.sistemabiblyjava.service.RealizarRenovacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/emprestimo")
@RestController
@AllArgsConstructor
public class EmprestimoRest {

    private final EmprestimoRepository repository;
    private final RealizarEmprestimoService realizarEmprestimoService;
    private final RealizarRenovacaoService realizarRenovacaoService;


    @GetMapping
    public ResponseEntity<List<Emprestimo>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Emprestimo> insert(@RequestBody Emprestimo resource){
        return ResponseEntity.ok(realizarEmprestimoService.exec(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/renovar-emprestimo/{id}")
    public ResponseEntity<Emprestimo> renovarEmprestimoDosLivros(@PathVariable Long id, @RequestBody Emprestimo resource){
        resource.setId(id);
        return ResponseEntity.ok(realizarRenovacaoService.exec(resource));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> update(@PathVariable Long id,@RequestBody Emprestimo resource){
        resource.setId(id);
        return ResponseEntity.ok(realizarEmprestimoService.exec(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
