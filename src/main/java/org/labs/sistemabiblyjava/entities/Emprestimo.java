package org.labs.sistemabiblyjava.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.labs.sistemabiblyjava.entities.databind.LivroDatabind;
import org.labs.sistemabiblyjava.entities.databind.SituacaoEmprestimoDatabind;
import org.labs.sistemabiblyjava.entities.databind.ClienteDatabind;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JsonSerialize(using = ClienteDatabind.IdSerializer.class)
    @JsonDeserialize(using = ClienteDatabind.IdDeserializer.class)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "emprestimo_livro",
            joinColumns = @JoinColumn(name = "emprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id"))
    @JsonSerialize(using = LivroDatabind.IdSerializer.class)
    @JsonDeserialize(using = LivroDatabind.IdDeserializer.class)
    private List<Livro> livro;

    @ManyToOne
    @JsonSerialize(using = SituacaoEmprestimoDatabind.IdSerializer.class)
    @JsonDeserialize(using = SituacaoEmprestimoDatabind.IdDeserializer.class)
    private SituacaoEmprestimo situacaoEmprestimo;
}
