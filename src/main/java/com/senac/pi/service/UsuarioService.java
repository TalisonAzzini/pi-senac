package com.senac.pi.service;

import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity salvar(UsuarioEntity usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public boolean loginExiste(String login) {
        return usuarioRepository.findByLogin(login) != null;
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
    
    public UsuarioEntity buscarPorLoginESenha(String login, String senha) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(login);
        if (usuario != null && usuario.getSenha().equals(senha)) { // Simples comparação sem criptografia
            return usuario;
        }
        return null;
    }
}