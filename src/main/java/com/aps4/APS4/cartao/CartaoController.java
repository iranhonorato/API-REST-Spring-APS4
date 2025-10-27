package com.aps4.APS4.cartao;

import com.aps4.APS4.autenticacao.UsuarioService;
import com.aps4.APS4.cartao.dto.CartaoRequestDTO;
import com.aps4.APS4.cartao.dto.CartaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<CartaoResponseDTO> listarCartoesController() {
        return cartaoService.listarCartoes();
    }

    @GetMapping("/{numero}")
    public CartaoResponseDTO buscarCartaoController(@PathVariable String numero) {
        return cartaoService.buscarPorNumero(numero);
    }

    @PostMapping
    public ResponseEntity<?> salvarCartaoController(
            @RequestBody CartaoRequestDTO cartao,
            @RequestHeader("Authorization") String token) {

        usuarioService.validarToken(token);
        CartaoResponseDTO novoCartao = cartaoService.salvarCartaoDTO(cartao);
        return ResponseEntity.ok(novoCartao);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<?> deletarCartaoController(
            @PathVariable String numero,
            @RequestHeader("Authorization") String token) {

        usuarioService.validarToken(token);
        cartaoService.deletar(numero);
        return ResponseEntity.noContent().build();
    }
}