package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "livroquantiaestoque")
@Data
public class LivroQuantiaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Livro livro;

    private Integer quantia;
}
