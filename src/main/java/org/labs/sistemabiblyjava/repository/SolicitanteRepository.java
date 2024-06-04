package org.labs.sistemabiblyjava.repository;


import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long>, JpaSpecificationExecutor<Solicitante> {
}
