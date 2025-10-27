package com.aps4.APS4.cliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;


@Schema(name = "ClienteDTO", description = "DTO do cliente (reponse)")
public record ClienteDTO (
//        Não tem o cpf do cliente porque é dado sensível

        @Schema(description="Nome do cliente", example="Iran") String nome,

        @Schema(description = "Data de nascimento do cliente", example = "1989-04-17")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dataNascimento,

        @Schema(description="Salario do cliente", example="1512.25") Float salario) {

}
