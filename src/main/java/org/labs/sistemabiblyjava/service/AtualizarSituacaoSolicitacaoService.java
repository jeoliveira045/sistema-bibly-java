package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;
import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.labs.sistemabiblyjava.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AtualizarSituacaoSolicitacaoService {
    private SolicitacaoRepository solicitacaoRepository;

    /**
     * Atualiza o status da solicitação mais antiga que foi atendida
     * @param resource
     */

    public void quandoEmprestimoRealizado(Optional<Solicitacao> resource) {
        SituacaoSolicitacao situacaoAtendida = new SituacaoSolicitacao();
        situacaoAtendida.setId(1L);
        situacaoAtendida.setDescricao("ATENTIDA");
        resource.get().setSituacaoSolicitacao(situacaoAtendida);
        solicitacaoRepository.save(resource.get());
    }
}
