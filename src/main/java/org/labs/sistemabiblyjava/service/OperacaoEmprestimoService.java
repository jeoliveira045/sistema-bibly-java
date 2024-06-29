package org.labs.sistemabiblyjava.service;

import jakarta.transaction.Transactional;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ClienteRepository;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


abstract class OperacaoEmprestimoService {

    protected LivroDisponiveisViewRepository livroDisponiveisViewRepository;
    protected LivroRepository livroRepository;
    protected EmprestimoRepository emprestimoRepository;
    protected ReservaRepository reservaRepository;
    protected ClienteRepository clienteRepository;

    protected AtualizarSituacaoSolicitacaoService atualizarSituacaoSolicitacao;

    @Transactional
    public Emprestimo exec(Emprestimo resource){
        validateQuantidadeDeLivrosEmprestados(resource);
        validateDoisLivrosDiferentes(resource);
        validateLivroDisponivel(resource);
        validateReservaMaisAntiga(resource);
        validateClienteCadastrado(resource);
        validateQuantidadeDeDiasDoEmprestimo(resource);
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
                .anyMatch(livro -> livroDisponiveisViewRepository
                        .findById(livro.getId())
                        .get()
                        .getQuantiaLivrosDisponiveis() <= 0
                );
        if(livroSolicitadoNaoDisponivelParaEmprestimo){
            throw new RuntimeException("Livro indisponível para emprestimo");
        }
    }

    public void validateQuantidadeDeLivrosEmprestados(Emprestimo resource){
        var maisDeDoisLivrosEmprestados = resource.getLivro().size() > 2;
        if(maisDeDoisLivrosEmprestados){
            throw new RuntimeException("A quantidade máxima de livros por emprestimo é 2");
        }
    }
    /**
     * Verifica se o solicitante do emprestimo é o mesmo da solicitacao mais antiga que está em espera
     * O objetivo desse método é garantir que as solicitações vão ser atendidas por ordem de data
     * @param resource
     */


    public void validateReservaMaisAntiga(Emprestimo resource){
        AtomicReference<Optional<Reserva>> reservaOptional = new AtomicReference<>();
        var clienteEmprestimoNotEqualClienteReservaMaisAntiga = resource.getLivro().stream().anyMatch(livro -> {
            reservaOptional.set(reservaRepository
                    .findAllByLivro_IdAndSituacaoReserva_Descricao(
                            livro.getId(), "EM ESPERA")
                    .stream()
                    .min(Comparator.comparing(Reserva::getDataReserva)));
            return reservaOptional.get().get().getCliente().getId() != resource.getCliente().getId();
        });
        if(clienteEmprestimoNotEqualClienteReservaMaisAntiga){
            throw new RuntimeException("O " +
                    "livro emprestado " +
                    "está reservado. Contate o ultimo cliente " +
                    "que está reservando esse livro ou " +
                    "coloque a reserva para \"Não Atendida\""
            );
        }
        atualizarSituacaoSolicitacao.quandoEmprestimoRealizado(reservaOptional.get());
    }

    public void validateDoisLivrosDiferentes(Emprestimo emprestimo){
        if(emprestimo.getLivro().size() > 2){
            var emprestimoComDoisLivrosIguais = emprestimo.getLivro().get(0) == emprestimo.getLivro().get(1);
            if(emprestimoComDoisLivrosIguais){
                throw new RuntimeException("O emprestimo não pode ter dois livros iguais");
            }
        }
    }

    public void validateClienteCadastrado(Emprestimo emprestimo){
        var clienteNaoExistente = clienteRepository.findById(emprestimo.getCliente().getId()).isEmpty();
        if(clienteNaoExistente){
            throw new RuntimeException("Cliente não encontrado na base");
        }

    }

    public void validateQuantidadeDeDiasDoEmprestimo(Emprestimo resource){
        LocalDate dataInicialDoEmprestimo = resource.getDtEmprestimoEm();
        LocalDate dataFinalDoEmprestimo = resource.getPrazoDevolucaoEm();

        Period periodoDeEmprestimo = Period.between(dataFinalDoEmprestimo, dataInicialDoEmprestimo);
        var periodoDeEmprestimoMaiorQueDezDias = periodoDeEmprestimo.getDays() > 10;

        if(periodoDeEmprestimoMaiorQueDezDias){
            throw new RuntimeException("O emprestimo não pode ser realizado pois o periodo de emprestimo é maior que 10 dias");
        }
    }
}
