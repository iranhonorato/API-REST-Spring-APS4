package com.aps4.APS4.cartao.dto;

import com.aps4.APS4.cartao.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Schema(name = "CartaoResponseDTO", description = "DTO do cartão retornado pela API")
public record CartaoResponseDTO(
//    Não tem numero do cartao porque é dado sensível

        @Schema(description = "Tipo do cartão", example="crédito")
        String tipo,

        @Schema(description = "Data de validade do cartão")
        LocalDate validade,

        @Schema(description = "Status atual do cartão", example="ATIVO")
        Cartao.CartaoStatus status
) {
    public CartaoResponseDTO(Cartao cartao) {
        this(
                cartao.getTipo(),
                cartao.getValidade(),
                cartao.getStatus()
        );
    }
}
