package org.labs.sistemabiblyjava.repository.vw;

import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroDisponiveisViewRepository extends JpaRepository<LivroDisponivelView, Long>, JpaSpecificationExecutor<LivroDisponivelView> {
}
