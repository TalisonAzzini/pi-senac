package com.senac.pi.repository;

import com.senac.pi.model.Pedido;
import com.senac.pi.model.enums.StatusPedido;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca pedidos por cliente
    List<Pedido> findByClienteId(Long clienteId);

    // Busca pedidos por vendedor
    List<Pedido> findByVendedorId(Long vendedorId);

    // Busca pedidos por status
    List<Pedido> findByStatus(StatusPedido status);

    // Consulta otimizada que carrega os itens em uma única query (evita N+1)
    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id")
    Pedido findByIdWithItens(@Param("id") Long id);

    // Consulta para relatório (total de vendas por período)
    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.data BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalVendasPeriodo(@Param("inicio") LocalDateTime inicio, 
                                        @Param("fim") LocalDateTime fim);

    // Consulta para produtos mais vendidos
    @Query("SELECT i.produto, SUM(i.quantidade) as total " +
           "FROM ItemPedido i " +
           "GROUP BY i.produto " +
           "ORDER BY total DESC")
    List<Object[]> findProdutosMaisVendidos();
}