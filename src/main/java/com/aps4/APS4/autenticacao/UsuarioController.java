package com.aps4.APS4.autenticacao;


import com.aps4.APS4.autenticacao.dto.UsuarioCreateDTO;
import com.aps4.APS4.autenticacao.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Collection<Usuario> listarUsuarioController() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuarioController(@RequestBody Usuario usuario) {
        Usuario  criado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody Usuario usuario) {
        String token = usuarioService.login(usuario);
        return ResponseEntity.ok(token);
    }


}
