package com.aps4.APS4.cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;


@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    public Collection<Cartao> listarCartoes() {
        return repository.findAll();
    }

    public Cartao buscarPorNumero(String numero) {
        return repository.findByNumeroCartao(numero);
    }

    public Cartao salvarCartao(Cartao cartao) {
        if (cartao == null || cartao.getNumeroCartao() == null || cartao.getNumeroCartao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número do cartão não pode ser nulo ou vazio.");
        }

        Cartao existente = repository.findByNumeroCartao(cartao.getNumeroCartao());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um cartão com esse número.");
        }

        repository.save(cartao);
        return cartao;
    }


    public void deletar(String numeroCartao) {
        Cartao existente = repository.findByNumeroCartao(numeroCartao);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não existe um cartao com esse número.");
        }
        repository.delete(existente);
    }

}