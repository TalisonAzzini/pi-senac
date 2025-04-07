package com.senac.pi.service;

import com.senac.pi.model.ItemPedidoEntity;
import com.senac.pi.model.PedidoEntity;
import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.repository.ItemPedidoRepository;
import com.senac.pi.repository.PedidoRepository;
import com.senac.pi.repository.ProdutoRepository;
import com.senac.pi.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoEntity criarPedido(Long vendedorId, Long clienteId) {
        UsuarioEntity vendedor = usuarioRepository.findById(vendedorId).orElseThrow();
        UsuarioEntity cliente = usuarioRepository.findById(clienteId).orElseThrow();
        
        PedidoEntity pedido = new PedidoEntity();
        pedido.setVendedor(vendedor);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.ZERO);
        
        return pedidoRepository.save(pedido);
    }

    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId).orElseThrow();
        ProdutoEntity produto = produtoRepository.findById(produtoId).orElseThrow();
        
        ItemPedidoEntity item = new ItemPedidoEntity();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));
        
        itemPedidoRepository.save(item);
        
        // Atualizar total do pedido
        BigDecimal novoTotal = pedido.getItens().stream()
            .map(ItemPedidoEntity::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        pedido.setTotal(novoTotal);
        pedidoRepository.save(pedido);
    }

    public List<PedidoEntity> listarTodos() {
        return pedidoRepository.findAll();
    }
}