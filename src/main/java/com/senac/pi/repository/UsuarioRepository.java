package com.senac.pi.repository;

import com.senac.pi.model.UsuarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByLogin(String login);
    UsuarioEntity findByEmail(String email);
    List<UsuarioEntity> findByTipo(String tipo);
}