package org.labs.sistemabiblyjava.repository.specification;

import org.labs.sistemabiblyjava.entities.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecification {
    public static Specification<Livro> byGenero(String genero){
        return (root, cq, cb) -> cb.like(root.get("genero"), "%" + genero + "%");
    }
}
