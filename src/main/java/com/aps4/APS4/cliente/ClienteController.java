package com.aps4.APS4.cliente;


import com.aps4.APS4.autenticacao.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Cliente> listarClientesController() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscarClienteController(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarCliente(cpf);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> editarClienteController(
            @PathVariable String cpf,
            @RequestBody Cliente cliente,
            @RequestHeader("Authorization") String token) {

        try {
            usuarioService.validarToken(token);
            Cliente clienteEditado = clienteService.editarCliente(cliente);
            return ResponseEntity.ok(clienteEditado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
