package com.aps4.APS4.cliente;


import com.aps4.APS4.autenticacao.Usuario;
import com.aps4.APS4.autenticacao.UsuarioService;
import com.aps4.APS4.cliente.dto.ClienteRequestDTO;
import com.aps4.APS4.cliente.dto.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<ClienteResponseDTO> listarClientesController() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscarClienteController(@PathVariable String cpf) {
        ClienteResponseDTO cliente = clienteService.buscarCliente(cpf);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarClienteController(
            @RequestBody ClienteRequestDTO cliente,
            @RequestHeader("Authorization") String token) {

        Usuario usuario = usuarioService.validarToken(token);
        ClienteResponseDTO clienteSalvo = clienteService.cadastrarCliente(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> editarClienteController(
            @PathVariable String cpf,
            @RequestBody ClienteRequestDTO cliente,
            @RequestHeader("Authorization") String token) {

        try {
            usuarioService.validarToken(token);
            ClienteResponseDTO clienteEditado = clienteService.editarCliente(cliente);
            return ResponseEntity.ok(clienteEditado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cpf}")
    public void deletarClienteController(@PathVariable String cpf, @RequestHeader(name = "token") String token) {
        Usuario usuario = usuarioService.validarToken(token);
        clienteService.deletarCliente(cpf);
    }
}
