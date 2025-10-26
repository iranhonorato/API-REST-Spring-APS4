package com.aps4.APS4.autenticacao;

import com.aps4.APS4.autenticacao.dto.UsuarioCreateDTO;
import com.aps4.APS4.autenticacao.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private final HashMap<String, Usuario> tokensDB = new HashMap<>();

    public Usuario cadastrarUsuario(Usuario novoUsuario) {
        if (repository.findByEmail(novoUsuario.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        String password = novoUsuario.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        novoUsuario.setPassword(hashedPassword);
        Usuario salvo = repository.save(novoUsuario);
        return salvo;
    }


    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }



    public String login(Usuario usuario) {
        Usuario userDB = repository.findByEmail(usuario.getEmail());
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        Usuario userDB = repository.findByEmail(usuario.getEmail());
        if (userDB == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        return userDB;
    }

}
