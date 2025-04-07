package com.senac.pi.controller;

import com.senac.pi.model.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    
    @GetMapping("/redirect-by-role")
    public String redirectByRole(HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        
        if (usuario != null && "VENDEDOR".equals(usuario.getTipo())) {
            return "adminMenu";
        }
        return "menu";
    }
}