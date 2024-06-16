package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.LivroQuantiaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroQuantiaEstoqueRepository extends JpaRepository<LivroQuantiaEstoque, Long> {
}
