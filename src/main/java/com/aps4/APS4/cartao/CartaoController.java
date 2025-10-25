package com.aps4.APS4.cartao;

import com.aps4.APS4.autenticacao.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public Optional<Cartao> buscarCartaoController(@PathVariable Integer id) {
        return cartaoService.buscarPorNumero(id);
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
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        usuarioService.validarToken(token);
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}