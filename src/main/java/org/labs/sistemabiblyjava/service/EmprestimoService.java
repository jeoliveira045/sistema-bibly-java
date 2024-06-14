package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Solicitacao;
import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.SolicitacaoRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    public void validateSolicitacaoMaisRecente(Emprestimo resource){
        var solicitacaoMaisAntiga = solicitacaoRepository
                .findAllByLivro_Id(resource.getLivro().getId())
                .stream()
                .min(Comparator.comparing(Solicitacao::getDataSolicitacao));


    }
}
