package com.aps4.APS4.cartao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "CartaoDTO", description = "DTO do cartão retornado pela API")
public record CartaoDTO(
        @Schema(description = "O número do cartão", example = "13546145") String numerCartao,
        @Schema(description = "Tipo do cartão", example = "Crédito ou Debito") String tipo,
        @Schema(description = "Status se está ativo ou não", example = "Ativo") String status,
        @Schema(description = "Validade do cartão", example = "2026-10-20")LocalDate validade,
        @Schema(description = "CPF do cliente que possui a conta", example = "123.456.789-00") String clienteCPF) {
}
