package org.labs.sistemabiblyjava.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ClienteRepository;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
@AllArgsConstructor
@Slf4j
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
    public boolean validateLivroDisponivel(Emprestimo resource){
        log.info("Verificando a quantidade de livros disponiveis do emprestimo {}", resource.getCliente().getId());
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

        return livroSolicitadoNaoDisponivelParaEmprestimo;
    }

    public void validateQuantidadeDeLivrosEmprestados(Emprestimo resource){
        log.info("Verificando a quantidade de livros do emprestimo {}", resource.getCliente().getId());
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
        log.info("Verificando se o emprestimo {} do livro não tem reservas", resource.getCliente().getId());
        AtomicReference<List<Reserva>> reservaList = new AtomicReference<>();
        var emprestimoRealizadoQuandoReservasExistem = resource.getLivro().stream().anyMatch(livro -> {
            reservaList.set(reservaRepository
                    .findAllByPrazoDevolucaoGreaterThanEqualAndDataEmprestimoEmIsLessThanEqualAndLivro_IdAndSituacaoReserva_Descricao(
                            resource.getDtEmprestimoEm(),
                            resource.getPrazoDevolucaoEm(),
                            livro.getId(),
                            "EM ESPERA"
                    ));
            if(!reservaList.get().isEmpty()){
                return !Objects.equals(reservaList.get()
                        .stream()
                        .min(Comparator.comparing(Reserva::getDataReserva))
                        .get().getCliente().getId(), resource.getCliente().getId())
                        &&
                        !reservaList.get().isEmpty()
                        &&
                        livroDisponiveisViewRepository.findById(livro.getId()).get().getQuantiaLivrosDisponiveis() == 1;
            }else{
                return false;
            }
        });

        if(emprestimoRealizadoQuandoReservasExistem){
            throw new RuntimeException("O " +
                    "livro emprestado " +
                    "está reservado. Contate o ultimo cliente " +
                    "que está reservando esse livro ou " +
                    "coloque a reserva para \"Não Atendida\""
            );
        }
        if(!reservaList.get().isEmpty()){
            atualizarSituacaoSolicitacao.quandoEmprestimoRealizado(reservaList.get()
                    .stream()
                    .min(Comparator.comparing(Reserva::getDataReserva)));
        }
    }

    public void validateDoisLivrosDiferentes(Emprestimo resource){
        log.info("Verificando se os livros do emprestimo {} são diferentes", resource.getCliente().getId());
        if(resource.getLivro().size() > 2){
            var emprestimoComDoisLivrosIguais = resource.getLivro().get(0) == resource.getLivro().get(1);
            if(emprestimoComDoisLivrosIguais){
                throw new RuntimeException("O emprestimo não pode ter dois livros iguais");
            }
        }
    }

    public void validateClienteCadastrado(Emprestimo resource){
        log.info("Verificando se o cliente do emprestimo {} existe na base de dados", resource.getCliente().getId());
        var clienteNaoExistente = clienteRepository.findById(resource.getCliente().getId()).isEmpty();
        if(clienteNaoExistente){
            throw new RuntimeException("Cliente não encontrado na base");
        }
    }

    public void validateQuantidadeDeDiasDoEmprestimo(Emprestimo resource){
        log.info("Verificando a quantidade de dias do emprestimo {}", resource.getCliente().getId());
        LocalDate dataInicialDoEmprestimo = resource.getDtEmprestimoEm();
        LocalDate dataFinalDoEmprestimo = resource.getPrazoDevolucaoEm();

        var periodoDeEmprestimo = ChronoUnit.DAYS.between(dataInicialDoEmprestimo, dataFinalDoEmprestimo);

        var periodoDeEmprestimoMaiorQueDezDias = periodoDeEmprestimo > 10L;

        if(periodoDeEmprestimoMaiorQueDezDias){
            throw new RuntimeException("O emprestimo não pode ser realizado pois o periodo de emprestimo é maior que 10 dias");
        }
    }
}
