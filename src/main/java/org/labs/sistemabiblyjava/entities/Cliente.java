package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Block;

import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

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
    @Column(name = "responsavel")
    private String responsavel;
    @Column(name = "cpfResponsavel")
    private String cpfResponsavel;
    @Column(name = "comprovanteresidencia")
    @Lob
    private Blob comprovanteresidencia;
    @Column(name = "certidaonascimento")
    @Lob
    private Blob certidaonascimento;
    @Column(name = "rg")
    private String rg;
    @Column(name = "numerotelefone")
    private String numerotelefone;
    @Column(name = "foto")
    @Lob
    private Blob foto;



}
