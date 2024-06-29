package org.labs.sistemabiblyjava.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Emprestimo;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ClienteRepository;
import org.labs.sistemabiblyjava.repository.LivroRepository;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class RealizarReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;

    @Transactional
    public Reserva exec(Reserva resource){
        validateClienteCadastrado(resource);
        validatePeriodosDistintos(resource);
        validateLivroExistente(resource);
        return reservaRepository.save(resource);
    }

    public void validateClienteCadastrado(Reserva emprestimo){
        var clienteNaoExistente = clienteRepository.findById(emprestimo.getCliente().getId()).isEmpty();
        if(clienteNaoExistente){
            throw new RuntimeException("Cliente não encontrado na base");
        }
    }

    public void validatePeriodosDistintos(Reserva resource){
        var reservaExistenteEmPeriodosSemelhantes = !reservaRepository.
                findAllByPrazoDevolucaoGreaterThanEqualAndDataEmprestimoEmIsLessThanEqualAndLivro_IdAndSituacaoReserva_Descricao(
                        resource.getDataEmprestimoEm(),
                        resource.getPrazoDevolucao(),
                        resource.getLivro().getId(),
                        "EM ESPERA").isEmpty();

        if(reservaExistenteEmPeriodosSemelhantes){
            throw new RuntimeException("Existem reservas durante o periodo solicitado. Verifique outro período.");
        }
    }

    public void validateLivroExistente(Reserva resource){
        var livroNaoExistente = !livroRepository.findById(resource.getLivro().getId()).isEmpty();

        if(livroNaoExistente){
            throw new RuntimeException("O livro não foi encontrado");
        }
    }
}
