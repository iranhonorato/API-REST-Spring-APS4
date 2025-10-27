package com.aps4.APS4.cartao;

import com.aps4.APS4.cartao.dto.CartaoRequestDTO;
import com.aps4.APS4.cartao.dto.CartaoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    public Cartao findByNumeroCartao(String numeroCartao);

}