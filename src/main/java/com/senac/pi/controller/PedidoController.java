package com.senac.pi.controller;

import com.senac.pi.model.ItemPedido;
import com.senac.pi.model.Pedido;
import com.senac.pi.model.Produto;
import com.senac.pi.model.enums.StatusPedido;
import com.senac.pi.service.PedidoService;
import com.senac.pi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
@SessionAttributes("pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    @GetMapping("/novo")
    public String iniciarPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "cadastroPedido";
    }

    @PostMapping("/adicionar-item")
    public String adicionarItem(
        @ModelAttribute("pedido") Pedido pedido,
        @RequestParam Long produtoId,
        @RequestParam Integer quantidade,
        RedirectAttributes attributes) {

        Produto produto = produtoService.buscarPorId(produtoId);
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        
        pedido.adicionarItem(item);
        
        attributes.addFlashAttribute("mensagem", "Item adicionado!");
        return "redirect:/pedidos/novo";
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(
        @ModelAttribute("pedido") Pedido pedido,
        SessionStatus sessionStatus) {

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoService.salvar(pedido);
        sessionStatus.setComplete();
        
        return "redirect:/pedidos/" + pedido.getId();
    }
}