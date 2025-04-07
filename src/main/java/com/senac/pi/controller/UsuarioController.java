package com.senac.pi.controller;

import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestParam String login, 
                       @RequestParam String senha,
                       HttpSession session) {

        UsuarioEntity usuario = usuarioService.buscarPorLoginESenha(login, senha);

        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/redirect-by-role";
        } else {
            return "redirect:/?erro=Login+ou+senha+inválidos";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioLogado");
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setTipo("CLIENTE");
        model.addAttribute("usuario", usuario);
        return "cadastroUsuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute UsuarioEntity usuario, Model model) {
        
        if (usuarioService.loginExiste(usuario.getLogin())) {
            model.addAttribute("mensagemErro", "Login já está em uso");
            return "cadastroUsuario";
        }
        
        if (usuarioService.emailExiste(usuario.getEmail())) {
            model.addAttribute("mensagemErro", "E-mail já está em uso");
            return "cadastroUsuario";
        }

        usuario.setTipo("CLIENTE");
        usuarioService.salvar(usuario);
        return "redirect:/usuarios/lista?sucesso";
    }

    @GetMapping("/lista")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "listaUsuarios";
    }
}