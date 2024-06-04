package org.labs.sistemabiblyjava.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.labs.sistemabiblyjava.entities.databind.LivroDatabind;
import org.labs.sistemabiblyjava.entities.databind.SolicitanteDatabind;

import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
@Data
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dtEmprestimoEm")
    private LocalDate dtEmprestimoEm;
    @Column(name = "prazoDevolucaoEm")
    private LocalDate prazoDevolucaoEm;
    @Column(name = "dataDevolucao")
    private LocalDate dataDevolucao;

    @ManyToOne()
    @JsonSerialize(using = SolicitanteDatabind.IdSerializer.class)
    @JsonDeserialize(using = SolicitanteDatabind.IdDeserializer.class)
    private Solicitante solicitante;

    @ManyToOne
    @JsonSerialize(using = LivroDatabind.IdSerializer.class)
    @JsonDeserialize(using = LivroDatabind.IdDeserializer.class)
    private Livro livro;
}
