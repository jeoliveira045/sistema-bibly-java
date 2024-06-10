package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.vw.LivroDisponivelView;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class EmprestimoService {

    private LivroDisponiveisViewRepository livroDisponiveisViewRepository;
    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;

    public Emprestimo exec(Emprestimo resource){
        validateLivroIsNotEmprestado(resource);
        emprestimoRepository.save(resource);
        return resource;
    }

    public void validateLivroIsNotEmprestado(Emprestimo resource){

        var livro = livroRepository.findById(resource.getLivro().getId());

        var livroSolicitadoNaoPresenteEmCatalogo = livroDisponiveisViewRepository.findByIsbn(livro.get().getIsbn()).stream().anyMatch(livroDisponivelView -> livroDisponivelView.getQuantiaLivrosDisponiveis() == 0);

        if(livroSolicitadoNaoPresenteEmCatalogo){
            throw new RuntimeException("Livro indisponível para emprestimo");
        }

    }
}
