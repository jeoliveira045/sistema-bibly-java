package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "editoras")
@Data
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeeditora;

    private String endereco;

    private String numerotelefone;
}
