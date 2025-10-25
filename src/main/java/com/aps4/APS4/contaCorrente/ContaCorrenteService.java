package com.aps4.APS4.contaCorrente;


import com.aps4.APS4.cliente.ClienteService;
import com.aps4.APS4.movimentacao.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
public class ContaCorrenteService {
    private final ClienteService cliente;

    @Autowired
    private ContaCorrenteRepository repository;


    public ContaCorrenteService(ClienteService cliente) {
        this.cliente = cliente;
    }


    public Collection<ContaCorrente> listarContas() {return repository.findAll();}



    public ContaCorrente cadastrarConta(ContaCorrente conta) {
        if (conta == null || conta.getConta() == null ||conta.getConta().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número da conta não pode ser nulo ou vazio");
        }

        Optional<ContaCorrente> existente = repository.findByConta(conta.getConta());
        if (existente != null) {
            throw new IllegalArgumentException("Já existe uma conta com esse número corrente");
        }

        ContaCorrente novoConta =repository.save(conta);
        return novoConta;
    };



    public List<Movimentacao> listarMovimentacoes(String conta) {
        ContaCorrente cc = repository.findByConta(conta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
        return cc.listaMovimentacoes();
    }


    public void depositar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
        existente.deposito(valor);
        repository.save(existente);
    }

    public void sacar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
        existente.saque(valor);
        repository.save(existente);
    }

}