package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.labs.sistemabiblyjava.repository.SolicitacaoRepository;
import org.labs.sistemabiblyjava.service.CancelarEmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/solicitacao")
@RestController
@AllArgsConstructor
public class SolicitacaoRest {
    private SolicitacaoRepository repository;
    private CancelarEmprestimoService cancelarEmprestimoService;


    @GetMapping
    public ResponseEntity<List<Solicitacao>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Solicitacao> insert(@RequestBody Solicitacao resource){
        return ResponseEntity.ok(repository.save(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitacao> update(@PathVariable Long id,@RequestBody Solicitacao resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @PutMapping("/cancelar-solicitacao/{id}")
    public ResponseEntity<Solicitacao> cancelarSolicitacao(@PathVariable Long id, @RequestBody Solicitacao resource){
        return ResponseEntity.ok(cancelarEmprestimoService.exec(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
