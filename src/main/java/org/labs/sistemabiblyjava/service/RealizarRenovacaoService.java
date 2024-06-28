package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroQuantiaEstoqueRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RealizarRenovacaoService {

    private ReservaRepository reservaRepository;
    private EmprestimoRepository emprestimoRepository;
    private LivroQuantiaEstoqueRepository livroQuantiaEstoqueRepository;


    public Emprestimo exec(Emprestimo resource){
        validateLivroEmEstoque(resource);
        return emprestimoRepository.save(resource);
    }

    public void validateLivroEmEstoque(Emprestimo emprestimo){
        var semLivroEmEstoque = emprestimo.getLivro().stream().anyMatch(livro -> {
            return livroQuantiaEstoqueRepository.findById(livro.getId()).get().getQuantia() <= 0;
        });

        var reservaRequisitadaParaLivro = emprestimo.getLivro().stream().anyMatch(livro -> {
            return reservaRepository.findAllByLivro_IdAndSituacaoReserva_Descricao(livro.getId(), "EM ESPERA").size() > 1;
        });

        if(reservaRequisitadaParaLivro && semLivroEmEstoque){
            throw new RuntimeException("Existem reservas para este livro e o estoque está vazio. Não é possivel realizar a renovação.");
        }
    }
}
