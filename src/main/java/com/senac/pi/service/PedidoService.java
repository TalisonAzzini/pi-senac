package com.senac.pi.service;

import com.senac.pi.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Transactional
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
