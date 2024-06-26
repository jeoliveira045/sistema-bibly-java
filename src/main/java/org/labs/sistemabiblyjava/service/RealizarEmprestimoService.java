package org.labs.sistemabiblyjava.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RealizarEmprestimoService {

    private LivroDisponiveisViewRepository livroDisponiveisViewRepository;
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;
    private ReservaRepository reservaRepository;

    private AtualizarSituacaoSolicitacaoService atualizarSituacaoSolicitacao;

    @Transactional
    public Emprestimo exec(Emprestimo resource){
        validateQuantidadeDeLivrosEmprestados(resource);
        validateDoisLivrosDiferentes(resource);
        validateLivroDisponivel(resource);
        validateReservaMaisAntiga(resource);
        resource = setDataDevolucaoDezDiasDepoisDataEmprestimoEm(resource);
        emprestimoRepository.save(resource);
        return resource;
    }

    /**
     * Verifica se o livro selecionado está disponível na biblioteca
     * @param resource
     */
    public void validateLivroDisponivel(Emprestimo resource){
        var livroSolicitadoNaoDisponivelParaEmprestimo = resource
                .getLivro()
                .stream()
                .anyMatch(livro -> livroDisponiveisViewRepository.findById(livro.getId()).get().getQuantiaLivrosDisponiveis() <= 0);
        if(livroSolicitadoNaoDisponivelParaEmprestimo){
            throw new RuntimeException("Livro indisponível para emprestimo");
        }
    }

    public void validateQuantidadeDeLivrosEmprestados(Emprestimo resource){
        var maisDeDoisLivrosEmprestados = resource.getLivro().size() > 2;
        if(maisDeDoisLivrosEmprestados){
            throw new RuntimeException("A quantidade máxima de livros que podem ser emprestados é 2");
        }
    }
    /**
     * Verifica se o solicitante do emprestimo é o mesmo da solicitacao mais antiga que está em espera
     * O objetivo desse método é garantir que as solicitações vão ser atendidas por ordem de data
     * @param resource
     */


    public void validateReservaMaisAntiga(Emprestimo resource){
        var clienteEmprestimoNotEqualClienteReservaMaisAntiga = resource.getLivro().stream().anyMatch(livro -> {
           var reserva = reservaRepository
                   .findAllByLivro_IdAndSituacaoReserva_Descricao(
                           livro.getId(), "EM ESPERA")
                   .stream()
                   .min(Comparator.comparing(Reserva::getDataReserva));
                return reserva.get().getCliente().getId() != resource.getCliente().getId();
        });
        if(clienteEmprestimoNotEqualClienteReservaMaisAntiga){
            throw new RuntimeException("O " +
                    "livro emprestado " +
                    "está reservado. Contate o ultimo cliente " +
                    "que está reservando esse livro ou " +
                    "coloque a reserva para \"Não Atendida\""
            );
        }
    }

    public void validateDoisLivrosDiferentes(Emprestimo emprestimo){
        var doisLivrosIguais = emprestimo.getLivro().get(0) == emprestimo.getLivro().get(1);
        if(doisLivrosIguais){
            throw new RuntimeException("O emprestimo não pode ter dois livros iguais");
        }
    }

    public Emprestimo setDataDevolucaoDezDiasDepoisDataEmprestimoEm(Emprestimo resource){
        resource.setDataDevolucao(resource.getDtEmprestimoEm().plusDays(10L));
        return resource;
    }



}
