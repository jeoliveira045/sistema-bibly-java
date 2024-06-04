package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {
}
