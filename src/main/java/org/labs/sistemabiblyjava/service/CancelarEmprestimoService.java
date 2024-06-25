package org.labs.sistemabiblyjava.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.SituacaoReserva;
import org.labs.sistemabiblyjava.entities.Reserva;
import org.labs.sistemabiblyjava.repository.ReservaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CancelarEmprestimoService {

    private final ReservaRepository reservaRepository;

    public Reserva exec(Reserva resource){
        SituacaoReserva cancelamento = new SituacaoReserva();
        cancelamento.setId(4l);
        cancelamento.setDescricao("CANCELADA");
        resource.setSituacaoReserva(cancelamento);
        return reservaRepository.save(resource);
    }
}
