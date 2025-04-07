package com.senac.pi.controller;

import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
            return "redirect:/?erro=Login+ou+senha+invalidos";
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
            model.addAttribute("usuario", usuario);
            return "cadastroUsuario";
        }
        
        if (usuarioService.emailExiste(usuario.getEmail())) {
            model.addAttribute("mensagemErro", "E-mail já está em uso");
            model.addAttribute("usuario", usuario);
            return "cadastroUsuario";
        }

        usuario.setTipo("CLIENTE");
        usuarioService.salvar(usuario);
        return "index";
    }

    @GetMapping("/lista")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "listaUsuarios";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    LocalDate localDate = LocalDate.parse(text, formatter);
                    setValue(Date.valueOf(localDate));
                } catch (Exception e) {
                    setValue(null);
                }
            }
        });
    }
}