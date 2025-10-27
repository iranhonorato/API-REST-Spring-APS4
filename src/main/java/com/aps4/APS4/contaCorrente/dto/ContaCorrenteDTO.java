package com.aps4.APS4.contaCorrente.dto;


import com.aps4.APS4.cartao.dto.CartaoDTO;
import com.aps4.APS4.cliente.dto.ClienteDTO;
import com.aps4.APS4.movimentacao.dto.MovimentacaoDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

@Schema(name = "ContaCorrenteDTO", description = "DTO de conta corrente (reponse)")
public record ContaCorrenteDTO(
        @Schema(description = "ID da conta no banco de dados", example = "12") Integer id,

        @Schema(description = "Número da agência", example = "0001") String agencia,

        @Schema(description = "Número da conta", example = "12345-6") String conta,

        @Schema(description = "Saldo disponível (em BRL)", example = "1500.00") Float saldo,

        @Schema(description = "Limite permitido (em BRL)", example = "500.00") Float limite,

        @Schema(description = "ID do cliente dono da conta", example = "9") Integer clienteId,

        @Schema(description = "Movimentações associadas (resumo)") ArrayList<MovimentacaoDTO> movimentacoes,

        @Schema(description = "Cartões associados (resumo)") ArrayList<CartaoDTO> cartoes
) {}

