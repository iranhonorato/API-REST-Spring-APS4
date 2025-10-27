package com.aps4.APS4.contaCorrente;


import com.aps4.APS4.cliente.ClienteService;
import com.aps4.APS4.contaCorrente.dto.ContaCorrenteRequestDTO;
import com.aps4.APS4.contaCorrente.dto.ContaCorrenteResponseDTO;
import com.aps4.APS4.movimentacao.Movimentacao;
import com.aps4.APS4.movimentacao.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository repository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;



    public List<ContaCorrenteRequestDTO> listarContas() {
        return repository.findAll()
                .stream()
                .map(ContaCorrenteRequestDTO::new)
                .collect(Collectors.toList());
    }

    public ContaCorrenteRequestDTO buscarConta(String conta) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada.");
        }
        return new ContaCorrenteRequestDTO(existente);
    }

    @Transactional
    public ContaCorrenteRequestDTO cadastrarConta(ContaCorrente conta) {
        if (conta == null || conta.getConta() == null || conta.getConta().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número da conta não pode ser nulo ou vazio");
        }

        ContaCorrente existente = repository.findByConta(conta.getConta());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma conta com esse número corrente");
        }

        ContaCorrente novaConta = repository.save(conta);
        return new ContaCorrenteRequestDTO(novaConta);
    }



    public ArrayList<Movimentacao> listarMovimentacoes(String conta) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta não encontrada.");
        }
        return existente.listaMovimentacoes();
    }


    @Transactional
    public ContaCorrenteRequestDTO depositar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada.");
        }
        existente.deposito(valor);
        ContaCorrente atualizado = repository.save(existente);
        return new ContaCorrenteRequestDTO(atualizado);
    }


    @Transactional
    public ContaCorrenteRequestDTO sacar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada.");
        }
        existente.saque(valor);
        ContaCorrente atualizado = repository.save(existente);
        return new ContaCorrenteRequestDTO(atualizado);
    }

}