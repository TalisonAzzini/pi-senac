package com.senac.pi.model;

import lombok.Data;

@Data
class ItemPedidoFrontDTO {
    private String produtoNome;
    private Integer quantidade;
    private Double preco;
    private Double subtotal;
}