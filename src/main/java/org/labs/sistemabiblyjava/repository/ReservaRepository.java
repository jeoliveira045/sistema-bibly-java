package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {
    List<Reserva> findAllByLivro_IdAndSituacaoReserva_Descricao(Long id, String descricao);


}
