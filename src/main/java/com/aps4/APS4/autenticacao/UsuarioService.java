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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private final HashMap<String, String> tokensDB = new HashMap<>();

    private UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(usuario.getId(), usuario.getEmail());
    }

    private Usuario fromCreateDTO(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());
        return usuario;
    }

    public UsuarioDTO cadastrarUsuario(UsuarioCreateDTO novoUsuarioDto) {
        Usuario novoUsuario = fromCreateDTO(novoUsuarioDto);
        if (repository.findByEmail(novoUsuario.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        String password = novoUsuario.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        novoUsuario.setPassword(hashedPassword);
        Usuario salvo = repository.save(novoUsuario);
        return toDTO(salvo);
    }


    public Collection<UsuarioDTO> listarUsuarios() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public UsuarioDTO buscarUsuario(String email) {
        Usuario userDB = repository.findByEmail(email);
        if (userDB == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return toDTO(userDB);
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
        tokensDB.put(token, usuario.getEmail()); // armazena apenas o email
        return token;
    }


    public UsuarioDTO validarToken(String token) {
        String email = tokensDB.get(token);
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        Usuario userDB = repository.findByEmail(email);
        if (userDB == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        return toDTO(userDB);
    }

}
