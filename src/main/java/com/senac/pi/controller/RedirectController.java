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
        
        if (usuario == null) {
            return "redirect:/";
        }
        
        if ("VENDEDOR".equals(usuario.getTipo())) {
            return "adminMenu"; // Retorna o template adminMenu.html
        }
        return "menu"; // Retorna o template menu.html
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/redirect-by-role";
        }
        return "index";
    }
}