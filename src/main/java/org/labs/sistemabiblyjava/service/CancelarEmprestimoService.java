package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;
import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.labs.sistemabiblyjava.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CancelarEmprestimoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public Solicitacao exec(Solicitacao resource){
        SituacaoSolicitacao cancelamento = new SituacaoSolicitacao();
        cancelamento.setId(4l);
        cancelamento.setDescricao("CANCELADA");
        resource.setSituacaoSolicitacao(cancelamento);
        return solicitacaoRepository.save(resource);
    }
}
