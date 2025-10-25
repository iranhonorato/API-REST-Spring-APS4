package com.aps4.APS4.cartao;

import com.aps4.APS4.autenticacao.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cartao> listarCartoesController() {
        return cartaoService.listarCartoes();
    }

    @GetMapping("/{numeroCartao}")
    public Cartao buscarCartaoController(@PathVariable String numeroCartao) {
        return cartaoService.buscarPorNumero(numeroCartao);
    }

    @PostMapping
    public ResponseEntity<?> salvarCartaoController(
            @RequestBody Cartao cartao,
            @RequestHeader("Authorization") String token) {

        usuarioService.validarToken(token);
        Cartao novoCartao = cartaoService.salvarCartao(cartao);
        return ResponseEntity.ok(novoCartao);
    }

    @DeleteMapping("/{numeroCartao}")
    public ResponseEntity<?> deletarCartaoController(
            @PathVariable String numeroCartao,
            @RequestHeader("Authorization") String token) {

        usuarioService.validarToken(token);
        cartaoService.deletar(numeroCartao);
        return ResponseEntity.noContent().build();
    }
}