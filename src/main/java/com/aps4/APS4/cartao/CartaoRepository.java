package com.aps4.APS4.cartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    Cartao findByNumeroCartao(String numeroCartao);
}