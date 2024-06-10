package org.labs.sistemabiblyjava.entities.vw;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Subselect;

@Entity
@Table(name = "livros_disponiveis")
@Data
@Subselect("""
    SELECT
        ID,
        NOME,
        ISBN,
        CONTAR_LIVROS_DISPONIVEIS
    FROM LIVROS_DISPONIVEIS
""")
public class LivroDisponivelView {
    @Id
    private Long id;
    private String nome;
    private String isbn;
    @Column(name = "CONTAR_LIVROS_DISPONIVEIS")
    private Integer quantiaLivrosDisponiveis;
}
