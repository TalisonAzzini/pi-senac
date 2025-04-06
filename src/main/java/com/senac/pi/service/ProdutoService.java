package com.senac.pi.service;

import com.senac.pi.model.Produto;
import com.senac.pi.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}