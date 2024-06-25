package org.labs.sistemabiblyjava.entities;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;
import org.labs.sistemabiblyjava.entities.databind.ColecaoDatabind;
import org.labs.sistemabiblyjava.entities.databind.EditoraDatabind;
import org.labs.sistemabiblyjava.entities.databind.SituacaoEmprestimoDatabind;

import java.time.LocalDate;

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

    @Column(name = "altura")
    private Double altura;

    @Column(name = "largura")
    private Double largura;

    @Column(name = "edicao")
    private String edicao;

    @Column(name = "dataedicao")
    private LocalDate dataEdicao;

    @ManyToOne
    @JsonSerialize(using = EditoraDatabind.IdSerializer.class)
    @JsonDeserialize(using = EditoraDatabind.IdDeserializer.class)
    private Editora editora;

    @ManyToOne
    @JsonSerialize(using = ColecaoDatabind.IdSerializer.class)
    @JsonDeserialize(using = ColecaoDatabind.IdDeserializer.class)
    private Colecao colecao;


}
