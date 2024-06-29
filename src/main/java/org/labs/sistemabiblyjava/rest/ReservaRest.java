package org.labs.sistemabiblyjava.rest;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.labs.sistemabiblyjava.service.CancelarReservaService;
import org.labs.sistemabiblyjava.service.RealizarReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reserva")
@RestController
@AllArgsConstructor
public class ReservaRest {
    private ReservaRepository repository;
    private CancelarReservaService cancelarReservaService;
    private RealizarReservaService realizarReservaService;


    @GetMapping
    public ResponseEntity<List<Reserva>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Reserva> insert(@RequestBody Reserva resource){
        return ResponseEntity.ok(realizarReservaService.exec(resource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findById(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable Long id, @RequestBody Reserva resource){
        resource.setId(id);
        return ResponseEntity.ok(repository.save(resource));
    }

    @PutMapping("/cancelar-reserva/{id}")
    public ResponseEntity<Reserva> cancelarSolicitacao(@PathVariable Long id, @RequestBody Reserva resource){
        return ResponseEntity.ok(cancelarReservaService.exec(resource));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        repository.deleteById(id);
    }
}
