package com.aps4.APS4.contaCorrente;


import com.aps4.APS4.cartao.Cartao;
import com.aps4.APS4.cliente.Cliente;
import com.aps4.APS4.movimentacao.Movimentacao;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.aps4.APS4.movimentacao.Movimentacao.TipoMovimentacao.DEPOSITO;
import static com.aps4.APS4.movimentacao.Movimentacao.TipoMovimentacao.SAQUE;

@Entity
@Table(name = "contas_correntes")
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "conta", nullable = false, unique = true)
    private String conta;

    @Column(nullable = false)
    private String agencia;

    @Column(nullable = false)
    private Float saldo;

    @Column(nullable = false)
    private Float limite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private final ArrayList<Movimentacao> movimentacoes = new ArrayList<>(); // N Movimentações para cada Conta Corrente

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private final ArrayList<Cartao> cartoes = new ArrayList<>(); // N Cartões para cada Conta Corrente



    public ContaCorrente() {}


    public ContaCorrente(String agencia, String conta, Float saldo, Float limite, Cliente cliente) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.limite = limite;
        this.cliente = cliente;
    }




    // Métodos get
    public String getAgencia() {return agencia;}
    public String getConta() {return conta;}
    public Cliente getCliente() {return cliente;}
    public Integer getId() {return id;}

    // Métodos set
    public void setAgencia(String agencia) {this.agencia = agencia;}
    public void setConta(String conta) {this.conta = conta;}




    public void saque(Float valor) {
        Float novoSaldo = this.saldo - valor;

        // Verifica se o valor é válido
        if (valor == null || valor < 0) {

            // Verifica se o novo saldo está dentro do limite permitido da conta
        } else if (novoSaldo >= -this.limite) {
            // Adiciona movimentacao à lista
            Movimentacao movimentacao = new Movimentacao(valor, LocalDate.now(), SAQUE);
            this.movimentacoes.add(movimentacao);

            // Atualiza saldo
            this.saldo = novoSaldo;
        }
    }

    public void deposito(Float valor)    {

        // Verifica se o valor é válido
        if (valor == null || valor < 0) {
            return;

        } else {
            // Adiciona movimentação à lista
            Movimentacao movimentacao = new Movimentacao(valor, LocalDate.now(), DEPOSITO);
            this.movimentacoes.add(movimentacao);

            // Atualiza saldo
            this.saldo += valor;
        }
    }

    public ArrayList<Movimentacao> listaMovimentacoes() {return movimentacoes;}
    public ArrayList<Cartao> listaCartoes() {return cartoes;}

    }

