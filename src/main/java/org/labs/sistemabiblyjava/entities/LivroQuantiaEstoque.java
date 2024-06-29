package org.labs.sistemabiblyjava.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.labs.sistemabiblyjava.entities.databind.LivroDatabind;

@Entity
@Table(name = "livroquantiaestoque")
@Data
public class LivroQuantiaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonSerialize(using = LivroDatabind.IdSerializer.class)
    @JsonDeserialize(using = LivroDatabind.IdDeserializer.class)
    private Livro livro;

    private Integer quantia;
}
