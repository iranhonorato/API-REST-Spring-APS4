package com.aps4.APS4.cartao;

import com.aps4.APS4.cartao.dto.CartaoRequestDTO;
import com.aps4.APS4.cartao.dto.CartaoResponseDTO;
import com.aps4.APS4.contaCorrente.ContaCorrenteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private ContaCorrenteService contaCorrenteService;


//  GET - Listar Cartões
    public List<CartaoResponseDTO> listarCartoes() {
        return repository.findAll()
                .stream()
                .map(CartaoResponseDTO::new) // usa o construtor direto
                .collect(Collectors.toList());
    }


//  GET - Listar um único cartão
    public CartaoResponseDTO buscarPorNumero(String numero) {
        Cartao cartao = repository.findByNumeroCartao(numero);
        if (cartao == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
        }
        return new CartaoResponseDTO(cartao);
    }


//  POST - Salvar cartão
    @Transactional
    public Cartao salvarCartaoDTO(CartaoRequestDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número do cartão não pode ser nulo ou vazio.");
        }

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(dto.numeroCartao());
        cartao.setTipo(dto.tipo());
        cartao.setStatus(dto.status());
        cartao.setValidade(dto.validade());

        if (cartao.getNumeroCartao() == null || cartao.getNumeroCartao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número do cartão não pode ser nulo ou vazio.");
        }
        return repository.save(cartao);
    }


//  DELETE - Deletar um cartão específico
    @Transactional
    public CartaoResponseDTO deletar(String numeroCartao) {
        Cartao existente = repository.findByNumeroCartao(numeroCartao);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
        }
        repository.delete(existente);
        return new CartaoResponseDTO(existente);
    }

}