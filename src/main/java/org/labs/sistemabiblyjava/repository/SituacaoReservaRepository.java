package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.SituacaoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoReservaRepository extends JpaRepository<SituacaoReserva, Long> {
}
