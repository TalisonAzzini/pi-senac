package com.senac.pi.controller;

import com.senac.pi.repository.ProdutoRepository;
import com.senac.pi.repository.UsuarioRepository;
import com.senac.pi.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/novo")
    public String novoPedido(Model model) {
        model.addAttribute("vendedores", usuarioRepository.findByTipo("VENDEDOR"));
        model.addAttribute("clientes", usuarioRepository.findByTipo("CLIENTE"));
        model.addAttribute("produtos", produtoRepository.findAll());
        return "cadastroPedido";
    }

    @PostMapping("/adicionar-item")
    @ResponseBody
    public ResponseEntity<?> adicionarItem(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            HttpSession session) {
        
        Long pedidoId = (Long) session.getAttribute("pedidoId");
        if (pedidoId == null) {
            return ResponseEntity.badRequest().body("Pedido não iniciado");
        }
        
        pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(HttpSession session) {
        session.removeAttribute("pedidoId");
        return "redirect:/pedidos/lista";
    }

    @GetMapping("/lista")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "listaPedidos";
    }
}