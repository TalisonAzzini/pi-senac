package com.senac.pi.repository;

import com.senac.pi.model.ListaDesejosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ListaDesejosRepository extends JpaRepository<ListaDesejosEntity, Long> {
    List<ListaDesejosEntity> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndProdutoId(Long usuarioId, Long produtoId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM ListaDesejosEntity l WHERE l.usuario.id = :usuarioId AND l.produto.id = :produtoId")
    void deleteByUsuarioIdAndProdutoId(Long usuarioId, Long produtoId);
}