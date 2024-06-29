package org.labs.sistemabiblyjava.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.labs.sistemabiblyjava.entities.Cliente;
import org.labs.sistemabiblyjava.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@AllArgsConstructor
@Slf4j
public class CadastrarClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente exec(Cliente resource){
        validateClienteMaiorDeIdade(resource);
        validateClienteNaoExistente(resource);
        return clienteRepository.save(resource);
    }

    public void validateClienteNaoExistente(Cliente resource){
        var clienteExistente = clienteRepository.findByCpf(resource.getCpf()).isPresent();

        if(clienteExistente){
            throw new RuntimeException("O cliente já está cadastrado na base");
        }
    }

    public void validateClienteMaiorDeIdade(Cliente resource){
        LocalDate dataDeNascimento = resource.getDatanascimento();
        LocalDate dataAtual = LocalDate.now();

        Period periodoEntreDatas = Period.between(dataAtual, dataDeNascimento);
        var clienteMenorDeIdade = periodoEntreDatas.getYears() < 12;

        if(clienteMenorDeIdade){
            throw new RuntimeException("O cliente precisa ter a idade mínima de 12 anos para se cadastrar. Solicite-o para cadastrar um de seus responsáveis");
        }
    }
}
