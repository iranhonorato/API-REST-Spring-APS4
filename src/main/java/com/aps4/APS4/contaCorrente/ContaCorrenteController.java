package com.aps4.APS4.contaCorrente;

import com.aps4.APS4.autenticacao.UsuarioService;
import com.aps4.APS4.cliente.ClienteService;
import com.aps4.APS4.movimentacao.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;


@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @GetMapping
    public Collection<ContaCorrente> listarContasController() {
        return contaCorrenteService.listarContas();
    }

    @GetMapping("/{conta}")
    public ArrayList<Movimentacao> listarMovimentacoesController(@PathVariable String conta) {
        return contaCorrenteService.listarMovimentacoes(conta);
    }


    @PostMapping
    public ResponseEntity<?> cadastrarContaController(
            @RequestBody ContaCorrente contaCorrente,
            @RequestHeader("Authorization") String token) {
        try {
            usuarioService.validarToken(token);
            ContaCorrente novaConta = contaCorrenteService.cadastrarConta(contaCorrente);
            return ResponseEntity.ok(novaConta);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{conta}/saque")
    public ResponseEntity<?> saqueController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token) {
        try {
            usuarioService.validarToken(token);
            contaCorrenteService.sacar(conta, valor);
            return ResponseEntity.ok("Saque realizado com sucesso");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{conta}/deposito")
    public ResponseEntity<?> depositarController(
            @PathVariable String conta,
            @RequestBody Float valor,
            @RequestHeader("Authorization") String token) {
        try {
            usuarioService.validarToken(token);
            contaCorrenteService.depositar(conta, valor);
            return ResponseEntity.ok("Depósito realizado com sucesso");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
