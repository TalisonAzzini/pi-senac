package com.senac.pi.service;

import com.senac.pi.model.ListaDesejosEntity;
import com.senac.pi.model.ProdutoEntity;
import com.senac.pi.model.UsuarioEntity;
import com.senac.pi.repository.ListaDesejosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaDesejosService {

    @Autowired
    private ListaDesejosRepository listaDesejosRepository;

    public List<ListaDesejosEntity> listarPorUsuario(Long usuarioId) {
        return listaDesejosRepository.findByUsuarioId(usuarioId);
    }

    public void adicionarProduto(UsuarioEntity usuario, ProdutoEntity produto) {
        if (!listaDesejosRepository.existsByUsuarioIdAndProdutoId(usuario.getId(), produto.getId())) {
            ListaDesejosEntity item = new ListaDesejosEntity();
            item.setUsuario(usuario);
            item.setProduto(produto);
            listaDesejosRepository.save(item);
        }
    }

    public void removerProduto(Long usuarioId, Long produtoId) {
        listaDesejosRepository.deleteByUsuarioIdAndProdutoId(usuarioId, produtoId);
    }
}