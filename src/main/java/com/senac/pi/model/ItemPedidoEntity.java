package com.senac.pi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "itens_pedido")
public class ItemPedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PedidoEntity pedido;

    @ManyToOne
    private ProdutoEntity produto;

    private Integer quantidade;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

}