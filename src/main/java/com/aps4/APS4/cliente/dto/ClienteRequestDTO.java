package com.aps4.APS4.cliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record ClienteRequestDTO (
    @Schema(description="CPF do cliente", example="11122233344")
    String cpf,

    @Schema(description="Nome do cliente", example="Iran")
    String nome,

    @Schema(description = "Data de nascimento do cliente", example = "1989-04-17")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dataNascimento,

    @Schema(description="Salario do cliente", example="1512.25") Float salario) {}
