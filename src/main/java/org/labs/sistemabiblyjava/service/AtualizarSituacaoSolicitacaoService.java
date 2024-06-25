package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.SituacaoReserva;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AtualizarSituacaoSolicitacaoService {
    private ReservaRepository reservaRepository;

    /**
     * Atualiza o status da solicitação mais antiga que foi atendida
     * @param resource
     */

    public void quandoEmprestimoRealizado(Optional<Reserva> resource) {
        SituacaoReserva situacaoAtendida = new SituacaoReserva();
        situacaoAtendida.setId(1L);
        situacaoAtendida.setDescricao("ATENTIDA");
        resource.get().setSituacaoReserva(situacaoAtendida);
        reservaRepository.save(resource.get());
    }
}
