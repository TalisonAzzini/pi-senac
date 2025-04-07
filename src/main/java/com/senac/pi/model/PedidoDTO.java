package com.senac.pi.model;

import java.util.List;
import lombok.Data;

@Data
public class PedidoDTO {
    private Long vendedorId;
    private Long clienteId;
    private List<ItemPedidoDTO> itens;
}