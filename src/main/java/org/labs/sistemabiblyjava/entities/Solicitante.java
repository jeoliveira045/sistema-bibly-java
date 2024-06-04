package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "solicitantes")
@Data
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "sobrenome")
    private String sobrenome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "genero")
    private String genero;
    @Column(name = "datanascimento")
    private LocalDate datanascimento;
}
