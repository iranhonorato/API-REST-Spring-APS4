package com.aps4.APS4.cartao.dto;

import com.aps4.APS4.cartao.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Schema(name = "CartaoRequestDTO", description = "DTO do cartão enviado à API")
public record CartaoRequestDTO(

        @Schema(description = "Número do cartão")
        String numeroCartao,

        @Schema(description = "Tipo do cartão", example="crédito")
        String tipo,

        @Schema(description = "Data de validade do cartão")
        LocalDate validade,

        @Schema(description = "Status atual do cartão", example="ATIVO")
        Cartao.CartaoStatus status
) { }