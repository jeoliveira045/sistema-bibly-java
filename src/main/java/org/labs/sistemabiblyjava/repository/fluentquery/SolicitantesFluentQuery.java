package org.labs.sistemabiblyjava.repository.fluentquery;

import org.labs.sistemabiblyjava.entities.Solicitante;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public class SolicitantesFluentQuery {

    public static Function<FluentQuery.FetchableFluentQuery<Solicitante>, List<Solicitante>> allSolicitantes(){
        return (solicitante) -> solicitante.all().stream().toList();
    }
}
