package com.aps4.APS4.autenticacao.dto;
import com.aps4.APS4.autenticacao.Usuario;



public record UsuarioDTO(Integer id, String email) {

    public static UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getEmail()
        );
    }
}