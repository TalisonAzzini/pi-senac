package com.senac.pi.model.enums;

public enum StatusPedido {
    ABERTO("Aberto"),          // Quando o pedido está em construção
    FINALIZADO("Finalizado"),  // Quando o pedido foi concluído
    CANCELADO("Cancelado"),    // Quando o pedido foi cancelado
    ENTREGUE("Entregue");      // Quando o pedido foi entregue

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}