package org.labs.sistemabiblyjava.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.labs.sistemabiblyjava.entities.databind.LivroDatabind;
import org.labs.sistemabiblyjava.entities.databind.SituacaoSolicitacaoDatabind;
import org.labs.sistemabiblyjava.entities.databind.ClienteDatabind;

import java.time.LocalDate;

@Entity
@Table(name = "solicitacoes")
@Data
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;
    @Column(name = "dt_emprestimo_em")
    private LocalDate dataEmprestimoEm;
    @Column(name = "prazo_devolucao")
    private LocalDate prazoDevolucao;

    @ManyToOne
    @JsonSerialize(using = ClienteDatabind.IdSerializer.class)
    @JsonDeserialize(using = ClienteDatabind.IdDeserializer.class)
    private Cliente cliente;

    @ManyToOne
    @JsonSerialize(using = LivroDatabind.IdSerializer.class)
    @JsonDeserialize(using = LivroDatabind.IdDeserializer.class)
    private Livro livro;

    @ManyToOne
    @JsonSerialize(using = SituacaoSolicitacaoDatabind.IdSerializer.class)
    @JsonDeserialize(using = SituacaoSolicitacaoDatabind.IdDeserializer.class)
    private SituacaoSolicitacao situacaoSolicitacao;

}
