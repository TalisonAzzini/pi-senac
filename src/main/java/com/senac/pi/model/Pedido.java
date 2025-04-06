package com.senac.pi.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Usuario vendedor;
    private Usuario cliente;
    private List<ItemPedido> itens = new ArrayList<>();
    private LocalDateTime dataPedido;
    private Float total;
}
