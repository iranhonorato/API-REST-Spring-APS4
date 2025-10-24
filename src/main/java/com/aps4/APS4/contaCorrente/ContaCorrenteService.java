package com.aps4.APS4.contaCorrente;


import com.aps4.APS4.cliente.ClienteService;
import com.aps4.APS4.movimentacao.Movimentacao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@Service
public class ContaCorrenteService {
    private final HashMap<String, ContaCorrente> contasDB = new HashMap<>();
    private final ClienteService cliente;

    public ContaCorrenteService(ClienteService cliente) {
        this.cliente = cliente;
    }


    public Collection<ContaCorrente> listarContas() {return contasDB.values();}

    public ContaCorrente cadastrarConta(ContaCorrente conta) {
        if (conta.getConta() == null || conta.getConta().isBlank()) {
            throw new IllegalArgumentException("O número da conta não pode ser nulo ou vazio");
        }

        if (contasDB.containsKey(conta.getConta())) {
            throw new IllegalArgumentException("Já existe uma conta com esse número corrente");
        }

        cliente.cadastrarCliente(conta.getCliente());
        contasDB.put(conta.getConta(), conta);
        return conta;
    };

    public ArrayList<Movimentacao> listarMovimentacoes(String conta) {
        ContaCorrente contaCorrente = contasDB.get(conta);
        if (contaCorrente == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        return contaCorrente.listaMovimentacoes();
    }


    public void depositar(String conta, Float valor) {
        ContaCorrente contaCorrente = contasDB.get(conta);
        if (contaCorrente == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        contaCorrente.deposito(valor);
    }

    public void sacar(String conta, Float valor) {
        ContaCorrente contaCorrente = contasDB.get(conta);
        if (contaCorrente == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }
        contaCorrente.saque(valor);
    }

}