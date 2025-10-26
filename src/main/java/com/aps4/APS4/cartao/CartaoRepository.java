package com.aps4.APS4.cartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    public Cartao findByNumeroCartao(String numeroCartao);

}