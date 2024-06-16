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
        var livroSolicitado = livroRepository.findById(resource.getLivro().getId()).orElseThrow(() -> new RuntimeException("Livro solicitado não encontrado"));

        var livroSolicitadoNaoDisponivelParaEmprestimo = livroDisponiveisViewRepository
                .findByIsbn(livroSolicitado.getIsbn())
                .stream()
                .anyMatch(livroDisponivelView -> livroDisponivelView.getQuantiaLivrosDisponiveis() == 0);

        if(livroSolicitadoNaoDisponivelParaEmprestimo){
            throw new RuntimeException("Livro indisponível para emprestimo");
        }
    }

    /**
     * Verifica se o solicitante do emprestimo é o mesmo da solicitacao mais antiga que não foi atendida
     * @param resource
     */

    public void validateSolicitacaoMaisAntiga(Emprestimo resource){
        var solicitacaoMaisAntiga = solicitacaoRepository
                .findAllByLivro_IdAndSituacaoSolicitacao_Descricao(resource.getLivro().getId(), "NÃO ATENDIDA")
                .stream()
                .min(Comparator.comparing(Solicitacao::getDataSolicitacao));
        boolean solicitanteNaoAtendido = resource.getSolicitante().getId() != solicitacaoMaisAntiga.get().getSolicitante().getId();
        if(solicitanteNaoAtendido){
            throw new RuntimeException("O(A) solicitante " + solicitacaoMaisAntiga.get().getSolicitante().getNome() + " está no aguardo do emprestimo do livro");
        }
        updateSituacaoSolicitacaoQuandoEmprestimoRealizado(solicitacaoMaisAntiga);

    }

    public void updateSituacaoSolicitacaoQuandoEmprestimoRealizado(Optional<Solicitacao> resource){
        SituacaoSolicitacao situacao = new SituacaoSolicitacao();
        situacao.setId(1L);
        situacao.setDescricao("ATENTIDA");
        resource.get().setSituacaoSolicitacao(situacao);
        solicitacaoRepository.save(resource.get());
    }
}
