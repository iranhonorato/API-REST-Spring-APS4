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
    public Collection<UsuarioDTO> listarUsuarioController() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioController(@PathVariable String email) {
        UsuarioDTO usuario = usuarioService.buscarUsuario(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuarioController(@RequestBody UsuarioCreateDTO usuarioDto) {
        UsuarioDTO  criado = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody Usuario usuario) {
        String token = usuarioService.login(usuario);
        return ResponseEntity.ok(token);
    }


}
