package com.senac.pi.service;

import com.senac.pi.model.Pedido;
import com.senac.pi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    
    @Transactional
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}