package org.labs.sistemabiblyjava.repository;

import org.labs.sistemabiblyjava.entities.Reserva;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {
    List<Reserva> findAllByLivro_IdAndSituacaoReserva_Descricao(Long id, String descricao);

    List<Reserva> findAllByPrazoDevolucaoGreaterThanEqualAndDataEmprestimoEmIsLessThanEqualAndLivro_IdAndSituacaoReserva_Descricao(LocalDate dtEmprestimo, LocalDate dtDevolucao, Long id, String situacao);
}
