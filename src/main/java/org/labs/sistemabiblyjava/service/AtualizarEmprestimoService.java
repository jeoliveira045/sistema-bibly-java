package org.labs.sistemabiblyjava.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.repository.*;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@AllArgsConstructor
public class AtualizarEmprestimoService extends OperacaoEmprestimoService {


    public AtualizarEmprestimoService(
            LivroDisponiveisViewRepository livroDisponiveisViewRepository,
            LivroRepository livroRepository,
            EmprestimoRepository emprestimoRepository,
            ReservaRepository reservaRepository,
            ClienteRepository clienteRepository,
            AtualizarSituacaoSolicitacaoService atualizarSituacaoSolicitacao) {
        super(livroDisponiveisViewRepository, livroRepository, emprestimoRepository, reservaRepository, clienteRepository, atualizarSituacaoSolicitacao);
    }

    @Transactional
    public Emprestimo exec(Emprestimo resource){
        validateQuantidadeDeLivrosEmprestados(resource);
        validateDoisLivrosDiferentes(resource);
        validateLivroDisponivel(resource);
        validateReservaMaisAntiga(resource);
        validateClienteCadastrado(resource);
        validateQuantidadeDeDiasDoEmprestimo(resource);
        validateLivroDisponivelEmEstoque(resource);
        return emprestimoRepository.save(resource);
    }

    public void validateLivroDisponivelEmEstoque(Emprestimo emprestimo){
        var semLivroDisponivelEmEstoque = emprestimo.getLivro().stream().anyMatch(livro -> {
            return livroDisponiveisViewRepository.findById(livro.getId()).get().getQuantiaLivrosDisponiveis() <= 0;
        });

        var existeReservaRequisitadaParaLivro = emprestimo.getLivro().stream().anyMatch(livro -> {
            return reservaRepository.findAllByLivro_IdAndSituacaoReserva_Descricao(livro.getId(), "EM ESPERA").size() > 1;
        });

        if(existeReservaRequisitadaParaLivro && semLivroDisponivelEmEstoque){
            throw new RuntimeException("Existem reservas para este livro e o estoque está vazio. Não é possivel realizar a renovação.");
        }
    }
}
