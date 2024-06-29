package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.repository.ClienteRepository;
import org.labs.sistemabiblyjava.repository.EmprestimoRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.labs.sistemabiblyjava.repository.vw.LivroDisponiveisViewRepository;
import org.springframework.stereotype.Service;

@Service

@Slf4j
public class AdicionarEmprestimoService extends OperacaoEmprestimoService{
    public AdicionarEmprestimoService(LivroDisponiveisViewRepository livroDisponiveisViewRepository, LivroRepository livroRepository, EmprestimoRepository emprestimoRepository, ReservaRepository reservaRepository, ClienteRepository clienteRepository, AtualizarSituacaoSolicitacaoService atualizarSituacaoSolicitacao) {
        super(livroDisponiveisViewRepository, livroRepository, emprestimoRepository, reservaRepository, clienteRepository, atualizarSituacaoSolicitacao);
    }
}
