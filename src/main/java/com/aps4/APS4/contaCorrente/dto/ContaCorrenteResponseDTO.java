package com.aps4.APS4.contaCorrente.dto;

import com.aps4.APS4.cartao.dto.CartaoRequestDTO;
import com.aps4.APS4.movimentacao.dto.MovimentacaoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

@Schema(name = "ContaCorrenteResponseDTO", description = "DTO de conta corrente (reponse)")
public record ContaCorrenteResponseDTO(
        @Schema(description = "Número da agência", example = "0001") String agencia,

        @Schema(description = "Saldo disponível (em BRL)", example = "1500.00") Float saldo,

        @Schema(description = "Limite permitido (em BRL)", example = "500.00") Float limite,

        @Schema(description = "Movimentações associadas (resumo)") ArrayList<MovimentacaoDTO> movimentacoes,

        @Schema(description = "Cartões associados (resumo)") ArrayList<CartaoRequestDTO> cartoes
) {}