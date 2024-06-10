package org.labs.sistemabiblyjava.repository.vw;

import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroDisponiveisViewRepository extends JpaRepository<LivroDisponivelView, Long>, JpaSpecificationExecutor<LivroDisponivelView> {
    Optional<LivroDisponivelView> findByIsbn(String isbn);
}
