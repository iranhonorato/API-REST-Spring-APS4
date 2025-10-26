package com.aps4.APS4.contaCorrente;


import com.aps4.APS4.cliente.ClienteService;
import com.aps4.APS4.movimentacao.Movimentacao;
import com.aps4.APS4.movimentacao.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository repository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;



    public Collection<ContaCorrente> listarContas() {return repository.findAll();}

    public ContaCorrente buscarConta(String conta) {return repository.findByConta(conta);}

    public ContaCorrente cadastrarConta(ContaCorrente conta) {
        if (conta == null || conta.getConta() == null ||conta.getConta().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número da conta não pode ser nulo ou vazio");
        }

        ContaCorrente existente = repository.findByConta(conta.getConta());
        if (existente != null) {
            throw new IllegalArgumentException("Já existe uma conta com esse número corrente");
        }

        ContaCorrente novaConta =repository.save(conta);
        return novaConta;
    };



    public ArrayList<Movimentacao> listarMovimentacoes(String conta) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta não encontrada.");
        }
        return existente.listaMovimentacoes();
    }


    public void depositar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta não encontrada.");
        }
        existente.deposito(valor);
        repository.save(existente);
    }

    public void sacar(String conta, Float valor) {
        ContaCorrente existente = repository.findByConta(conta);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta não encontrada.");
        }
        existente.saque(valor);
        repository.save(existente);
    }

}