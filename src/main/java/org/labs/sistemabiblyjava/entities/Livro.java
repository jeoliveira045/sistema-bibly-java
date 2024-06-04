package org.labs.sistemabiblyjava.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "livros")
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "genero")
    private String genero;
    @Column(name = "autor")
    private String autor;
}
