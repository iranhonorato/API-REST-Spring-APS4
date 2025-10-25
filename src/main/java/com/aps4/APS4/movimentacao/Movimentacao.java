package com.aps4.APS4.movimentacao;

import com.aps4.APS4.contaCorrente.ContaCorrente;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Float valor;

    @Column(nullable = false)
    private LocalDate data;

    public enum TipoMovimentacao {SAQUE, DEPOSITO}

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaCorrente conta;

    public Movimentacao(Float valor, LocalDate data, TipoMovimentacao tipo) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    // Métodos get
    public Integer getId() {return id;}
    public Float getValor() {return valor;}
    public LocalDate getData() {return data;}
    public TipoMovimentacao getTipo() {return tipo;}
}