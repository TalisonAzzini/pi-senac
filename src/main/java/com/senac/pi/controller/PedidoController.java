package com.senac.pi.controller;

import com.senac.pi.model.ItemPedidoDTO;
import com.senac.pi.model.ItemPedidoEntity;
import com.senac.pi.model.PedidoDTO;
import com.senac.pi.model.PedidoEntity;
import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.repository.ProdutoRepository;
import com.senac.pi.repository.UsuarioRepository;
import com.senac.pi.service.PedidoService;
import com.senac.pi.service.ProdutoService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //OK
    @GetMapping("/novo")
    public String novoPedido(Model model) {
        model.addAttribute("vendedores", usuarioRepository.findByTipo("VENDEDOR"));
        model.addAttribute("clientes", usuarioRepository.findByTipo("CLIENTE"));
        model.addAttribute("produtos", produtoRepository.findAll());
        return "cadastroPedido";
    }

    //OK
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

    //OK
    @PostMapping("/finalizar")
    public ResponseEntity<String> finalizarPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            PedidoEntity pedido = pedidoService.criarPedido(
                pedidoDTO.getVendedorId(), 
                pedidoDTO.getClienteId()
            );

            for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
                pedidoService.adicionarItem(
                    pedido.getId(),
                    itemDTO.getProdutoId(),
                    itemDTO.getQuantidade()
                );
            }

            return ResponseEntity.ok("Pedido finalizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("Erro ao finalizar pedido: " + e.getMessage());
        }
    }

    //OK
    @GetMapping("/lista")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "listaPedidos";
    }
    
    @GetMapping("/detalhes/{id}")
    public String detalhesPedido(@PathVariable Long id, Model model) {
        PedidoEntity pedido = pedidoService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        model.addAttribute("pedido", pedido);
        return "detalhesPedido";
    }
    
}