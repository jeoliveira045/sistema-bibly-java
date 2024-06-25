package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long>, JpaSpecificationExecutor<Editora> {
}
