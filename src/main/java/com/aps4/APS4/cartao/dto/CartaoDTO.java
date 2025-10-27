package com.aps4.APS4.cartao.dto;

import com.aps4.APS4.cartao.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;


@Schema(name = "CartaoDTO", description = "DTO do cartão retornado pela API")
public class CartaoDTO {
    public enum CartaoStatus {ATIVO, CANCELADO}

//    Não tem numero do cartao porque é dado sensível

    @Schema(description = "Tipo do cartão", example="crédito")
    private String tipo;

    @Schema(description = "Data de validade do cartão")
    private LocalDate validade;

    @Schema(description = "Status atual do cartão", example="ATIVO")
    private CartaoStatus status;

    public CartaoDTO() {}

    public CartaoDTO(Cartao cartao) {
        BeanUtils.copyProperties(cartao, this);
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public CartaoStatus getStatus() {
        return status;
    }

    public void setStatus(CartaoStatus status) {
        this.status = status;
    }
}
