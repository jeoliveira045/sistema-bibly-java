package org.labs.sistemabiblyjava.repository.specification;

import org.labs.sistemabiblyjava.entities.Solicitante;
import org.springframework.data.jpa.domain.Specification;

public class SolicitanteSpecification {

    public static Specification<Solicitante> firstNameFirstLetter(String firstNameFirstLetter){
        return (root, cq, cb) -> {
            return cb.equal(root.get("nome"), firstNameFirstLetter);
        };
    }
}
