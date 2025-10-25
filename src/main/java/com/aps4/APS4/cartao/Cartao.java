package com.aps4.APS4.cartao;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "numero_cartao", unique = true, nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate validade;


    public enum CartaoStatus {ATIVO, CANCELADO}

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartaoStatus status;

    public Cartao() {}

    public Cartao(String numeroCartao, String tipo, LocalDate validade, CartaoStatus status) {
        this.numeroCartao = numeroCartao;
        this.tipo = tipo;
        this.validade = validade;
        this.status = status;
    }

    // Métodos get
    public Integer getId() {return this.id;}
    public String getNumeroCartao() {return this.numeroCartao;}
    public String getTipo() {return this.tipo;}
    public LocalDate getValidade() {return this.validade;}
    public CartaoStatus getStatus() {return this.status;}

    // Métodos set
    public void setNumeroCartao(String numeroCartao) {this.numeroCartao = numeroCartao;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setValidade(LocalDate validade) {this.validade = validade;}
    public void setStatus(CartaoStatus status) {this.status = status;}

    public Boolean isExpired() {
        return LocalDate.now().isAfter(this.validade);
    };

    public Boolean cancelaCartao() {
        if (this.status == CartaoStatus.ATIVO) {
            this.status = CartaoStatus.CANCELADO;
            return true;
        }
        return false;
    };

}
