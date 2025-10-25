package com.aps4.APS4.cliente;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column
    private Float salario;

    public Cliente() {}

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Float salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }

    // Métodos get
    public String getNome() {return this.nome;}
    public String getCpf() {return this.cpf;}
    public LocalDate getDataNascimento() {return this.dataNascimento;}
    public Float getSalario() {return this.salario;}


    // Métodos set
    public void setNome(String nome) {this.nome = nome;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}
    public void setSalario(Float salario) {this.salario = salario;}

}