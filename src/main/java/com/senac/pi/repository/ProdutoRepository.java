package com.senac.pi.repository;

import com.senac.pi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca por nome (case insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Busca produtos com estoque baixo
    @Query("SELECT p FROM Produto p WHERE p.estoque < :estoqueMinimo")
    List<Produto> findProdutosComEstoqueBaixo(@Param("estoqueMinimo") int estoqueMinimo);

    // Consulta para produtos nunca vendidos
    @Query("SELECT p FROM Produto p WHERE p.id NOT IN (SELECT DISTINCT i.produto.id FROM ItemPedido i)")
    List<Produto> findProdutosNuncaVendidos();

    @Query("SELECT p FROM Produto p")
    List<Produto> findAll();
}