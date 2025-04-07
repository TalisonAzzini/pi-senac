package com.senac.pi.controller;

import com.senac.pi.model.ItemPedidoDTO;
import com.senac.pi.model.ItemPedidoEntity;
import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.repository.ProdutoRepository;
import com.senac.pi.repository.UsuarioRepository;
import com.senac.pi.service.PedidoService;
import com.senac.pi.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    private ProdutoService produtoService;
    
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
    public ResponseEntity<ItemPedidoEntity> adicionarItem(@RequestBody ItemPedidoDTO itemDTO) {
        ProdutoEntity produto = produtoService.buscarPorId(itemDTO.getProdutoId());

        if (produto == null) {
            return ResponseEntity.badRequest().build();
        }

        Integer quantidade = itemDTO.getQuantidade();
        BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

        ItemPedidoEntity item = new ItemPedidoEntity();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setSubtotal(subtotal);

        return ResponseEntity.ok(item);
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