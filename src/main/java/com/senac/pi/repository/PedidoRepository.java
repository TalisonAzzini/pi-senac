package com.senac.pi.repository;

import com.senac.pi.model.PedidoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    @Query("SELECT p FROM PedidoEntity p JOIN FETCH p.itens WHERE p.id = :id")
    Optional<PedidoEntity> findByIdWithItens(@Param("id") Long id);

}