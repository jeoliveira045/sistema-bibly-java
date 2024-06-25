package org.labs.sistemabiblyjava.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "situacaosolicitacao")
@Data
@NoArgsConstructor
public class SituacaoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descricao;

}
