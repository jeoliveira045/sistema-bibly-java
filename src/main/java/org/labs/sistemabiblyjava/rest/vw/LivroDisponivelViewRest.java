package org.labs.sistemabiblyjava.rest.vw;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livro-disponivel")
@AllArgsConstructor
public class LivroDisponivelViewRest {

    private LivroDisponiveisViewRepository repository;

    @GetMapping
    public ResponseEntity<List<?>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

}
