package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoSolicitacaoRepository extends JpaRepository<SituacaoSolicitacao, Long> {
}
