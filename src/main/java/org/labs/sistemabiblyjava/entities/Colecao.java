package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colecoes")
@Data
public class Colecao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

}

