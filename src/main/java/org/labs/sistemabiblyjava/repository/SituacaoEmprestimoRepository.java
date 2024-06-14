package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.SituacaoEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoEmprestimoRepository extends JpaRepository<SituacaoEmprestimo, Long> {
}
