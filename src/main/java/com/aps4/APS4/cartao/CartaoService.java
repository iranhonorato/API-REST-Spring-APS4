package com.aps4.APS4.cartao;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;


@Service
public class CartaoService {
    // Banco de dados em memória
    private final HashMap<String, Cartao> cartoes = new HashMap<>();

    public Collection<Cartao> listarCartoes() {
        return cartoes.values();
    }

    public Cartao buscarPorNumero(String numeroCartao) {
        return cartoes.get(numeroCartao);
    }

    public Cartao salvarCartao(Cartao cartao) {
        if (cartao.getNumeroCartao() == null || cartao.getNumeroCartao().isEmpty()) {
            throw new IllegalArgumentException("O número do cartão não pode ser nulo ou vazio.");
        }
        // Verifica duplicidade
        if (cartoes.containsKey(cartao.getNumeroCartao())) {
            throw new IllegalArgumentException("Já existe um cartão com esse número.");
        }

        cartoes.put(cartao.getNumeroCartao(), cartao);
        return cartao;
    }


    public void deletar(String numeroCartao) {
        cartoes.remove(numeroCartao);

    }

}