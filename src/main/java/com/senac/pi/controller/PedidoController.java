package com.senac.pi.controller;

import com.senac.pi.model.ItemPedido;
import com.senac.pi.model.Pedido;
import com.senac.pi.model.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
@SessionAttributes("pedido")
public class PedidoController {

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
        sessionStatus.setComplete(); // Limpa o pedido da sessão
        
        return "redirect:/pedidos/" + pedido.getId();
    }
}