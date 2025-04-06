package com.senac.pi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public String encodeSenha(String senha) {
        return passwordEncoder.encode(senha);
    }
    
    public boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return passwordEncoder.matches(senhaDigitada, senhaArmazenada);
    }
}