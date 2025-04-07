package com.senac.pi.controller;

import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.service.ListaDesejosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lista/desejos")
public class ListaDesejosController {

    @Autowired
    private ListaDesejosService listaDesejosService;

    @GetMapping
    public String mostrarListaDesejos(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/?erro=Necessario+estar+logado";
        }
        
        model.addAttribute("itens", listaDesejosService.listarPorUsuario(usuario.getId()));
        model.addAttribute("nomeUsuario", usuario.getNome());
        return "listaDesejos";
    }

    @PostMapping("/remover")
    public String removerDaLista(@RequestParam Long produtoId, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/?erro=Necessario+estar+logado";
        }
        
        listaDesejosService.removerProduto(usuario.getId(), produtoId);
        return "redirect:/lista/desejos?sucesso=Produto+removido+da+lista";
    }
}