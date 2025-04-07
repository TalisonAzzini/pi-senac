package com.senac.pi.service;

import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoEntity> listarTodos() {
        return produtoRepository.findAll();
    }
    
    public ProdutoEntity salvar(ProdutoEntity produto) {
        return produtoRepository.save(produto);
    }
    
    public ProdutoEntity buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
}