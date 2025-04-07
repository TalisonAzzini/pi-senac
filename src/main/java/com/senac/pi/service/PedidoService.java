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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        UsuarioEntity vendedor = usuarioRepository.findById(vendedorId)
            .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
        UsuarioEntity cliente = usuarioRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        PedidoEntity pedido = new PedidoEntity();
        pedido.setVendedor(vendedor);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setItens(new ArrayList<>());

        return pedidoRepository.save(pedido);
    }

    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        ProdutoEntity produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedidoEntity item = new ItemPedidoEntity();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));

        itemPedidoRepository.save(item);

        pedido.getItens().add(item);

        BigDecimal novoTotal = pedido.getItens().stream()
            .map(ItemPedidoEntity::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(novoTotal);

        pedidoRepository.save(pedido);
    }

    //OK
    public List<PedidoEntity> listarTodos() {
        return pedidoRepository.findAll();
    }
}