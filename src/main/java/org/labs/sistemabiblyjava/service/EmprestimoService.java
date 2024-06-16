package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.SituacaoSolicitacao;
import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.SolicitacaoRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmprestimoService {

    private LivroDisponiveisViewRepository livroDisponiveisViewRepository;
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;
    private SolicitacaoRepository solicitacaoRepository;

    public Emprestimo exec(Emprestimo resource){
        validateLivroDisponivel(resource);
        validateSolicitacaoMaisAntiga(resource);
        emprestimoRepository.save(resource);
        return resource;
    }

    /**
     * Verifica se o livro selecionado está disponível na biblioteca
     * @param resource
     */
    public void validateLivroDisponivel(Emprestimo resource){

        var livroSolicitadoNaoDisponivelParaEmprestimo = livroDisponiveisViewRepository
                .findById(resource.getLivro().getId())
                .stream()
                .anyMatch(livroDisponivelView -> livroDisponivelView.getQuantiaLivrosDisponiveis() == 0);

        if(livroSolicitadoNaoDisponivelParaEmprestimo){
            throw new RuntimeException("Livro indisponível para emprestimo");
        }
    }

    /**
     * Verifica se o solicitante do emprestimo é o mesmo da solicitacao mais antiga que está em espera
     * @param resource
     */

    public void validateSolicitacaoMaisAntiga(Emprestimo resource){
        var solicitacaoMaisAntiga = solicitacaoRepository
                .findAllByLivro_IdAndSituacaoSolicitacao_Descricao(resource.getLivro().getId(), "EM ESPERA")
                .stream()
                .min(Comparator.comparing(Solicitacao::getDataSolicitacao));
        boolean solicitanteNaoAtendido = resource.getSolicitante().getId() != solicitacaoMaisAntiga.get().getSolicitante().getId();
        if(solicitanteNaoAtendido){
            throw new RuntimeException("O(A) solicitante " + solicitacaoMaisAntiga.get().getSolicitante().getNome() + " está no aguardo do emprestimo do livro. Entre em contato com solicitante ou mude o status da solicitação para Não atendida");
        }
        updateSituacaoSolicitacaoQuandoEmprestimoRealizado(solicitacaoMaisAntiga);
    }

    /**
     * Atualiza o status da solicitação mais antiga que foi atendida
     * @param resource
     */

    public void updateSituacaoSolicitacaoQuandoEmprestimoRealizado(Optional<Solicitacao> resource){
        SituacaoSolicitacao situacaoAtendida = new SituacaoSolicitacao();
        situacaoAtendida.setId(1L);
        situacaoAtendida.setDescricao("ATENTIDA");
        resource.get().setSituacaoSolicitacao(situacaoAtendida);
        solicitacaoRepository.save(resource.get());
    }
}
