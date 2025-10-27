package com.aps4.APS4.movimentacao.dto;

import com.aps4.APS4.movimentacao.Movimentacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "MovimentacaoDTO", description = "DTO de resposta de movimentação financeira")
public record MovimentacaoDTO(

        @Schema(description = "Valor da movimentação", example = "250.75")
        Float valor,

        @Schema(description = "Data da movimentação", example = "2025-10-27")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate data,

        @Schema(description = "Tipo da movimentação (SAQUE ou DEPOSITO)", example = "DEPOSITO")
        Movimentacao.TipoMovimentacao tipo
) {}