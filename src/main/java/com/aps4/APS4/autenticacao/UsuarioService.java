package com.aps4.APS4.autenticacao;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

    private final HashMap<String, Usuario> usuariosDB = new HashMap<>();
    private final HashMap<String, Usuario> tokensDB = new HashMap<>();


    public Usuario cadastrarUsuario(Usuario novoUsuario) {
        String password = novoUsuario.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        novoUsuario.setPassword(hashedPassword);
        usuariosDB.put(novoUsuario.getEmail(), novoUsuario);
        return novoUsuario;
    }


    public Collection<Usuario> listarUsuarios() {
        return usuariosDB.values();
    };


    public Usuario buscarUsuario(String email) {
        return usuariosDB.get(email);
    }


    public String login(Usuario usuario) {
        Usuario userDB = usuariosDB.get(usuario.getEmail());
        if (userDB == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado");
        }
        if (!BCrypt.checkpw(usuario.getPassword(), userDB.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha inválida");
        }

        return gerarToken(userDB);
    }


    public String gerarToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        tokensDB.put(token, usuario);
        return token;
    }


    public Usuario validarToken(String token) {
        Usuario usuario = tokensDB.get(token);

        if (usuario == null) {
            throw new RuntimeException("Token Inválido");
        }
        return usuario;
    }

}
