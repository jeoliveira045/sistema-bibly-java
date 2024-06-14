package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, JpaSpecificationExecutor<Solicitacao> {
    List<Solicitacao> findAllByLivro_Id(Long id);
}
