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
        validateLivroDisponivelEmEstoque(resource);
        return emprestimoRepository.save(resource);
    }

    public void validateLivroDisponivelEmEstoque(Emprestimo emprestimo){
        var semLivroDisponivelEmEstoque = emprestimo.getLivro().stream().anyMatch(livro -> {
            return livroQuantiaEstoqueRepository.findById(livro.getId()).get().getQuantia() <= 0;
        });

        var existeReservaRequisitadaParaLivro = emprestimo.getLivro().stream().anyMatch(livro -> {
            return reservaRepository.findAllByLivro_IdAndSituacaoReserva_Descricao(livro.getId(), "EM ESPERA").size() > 1;
        });

        if(existeReservaRequisitadaParaLivro && semLivroDisponivelEmEstoque){
            throw new RuntimeException("Existem reservas para este livro e o estoque está vazio. Não é possivel realizar a renovação.");
        }
    }
}
